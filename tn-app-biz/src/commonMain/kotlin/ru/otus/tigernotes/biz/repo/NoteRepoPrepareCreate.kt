package ru.otus.tigernotes.biz.repo

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import ru.otus.tigernotes.common.TnContext
import ru.otus.tigernotes.common.models.TnState

fun ICorAddExecDsl<TnContext>.repoPrepareCreate(title: String) = worker {
    this.title = title
    description = "Подготовка объекта к сохранению в базе данных"
    on { state == TnState.RUNNING }
    handle {
        noteRepoRead = noteValidated.deepCopy()
        noteRepoRead.ownerId = principal.id
        noteRepoPrepare = noteRepoRead
    }
}
