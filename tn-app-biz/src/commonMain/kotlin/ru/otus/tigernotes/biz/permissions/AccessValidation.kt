package ru.otus.tigernotes.biz.permissions

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.chain
import com.crowdproj.kotlin.cor.handlers.worker
import ru.otus.tigernotes.auth.checkPermitted
import ru.otus.tigernotes.auth.resolveRelationsTo
import ru.otus.tigernotes.common.TnContext
import ru.otus.tigernotes.common.helpers.fail
import ru.otus.tigernotes.common.models.TnError
import ru.otus.tigernotes.common.models.TnState

fun ICorAddExecDsl<TnContext>.accessValidation(title: String) = chain {
    this.title = title
    description = "Вычисление прав доступа по группе принципала и таблице прав доступа"
    on { state == TnState.RUNNING }
    worker("Вычисление отношения объявления к принципалу") {
        noteRepoRead.principalRelations = noteRepoRead.resolveRelationsTo(principal)
    }
    worker("Вычисление доступа к объявлению") {
        permitted = checkPermitted(command, noteRepoRead.principalRelations, permissionsChain)
    }
    worker {
        this.title = "Валидация прав доступа"
        description = "Проверка наличия прав для выполнения операции"
        on { !permitted }
        handle {
            fail(TnError(message = "User is not allowed to perform this operation"))
        }
    }
}

