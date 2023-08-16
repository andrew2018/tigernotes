package ru.otus.tigernotes.biz.repo

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import ru.otus.tigernotes.common.TnContext
import ru.otus.tigernotes.common.models.TnState

fun ICorAddExecDsl<TnContext>.repoPrepareUpdate(title: String) = worker {
    this.title = title
    description = "Готовим данные к сохранению в БД: совмещаем данные, прочитанные из БД, " +
            "и данные, полученные от пользователя"
    on { state == TnState.RUNNING }
    handle {
        noteRepoPrepare = noteRepoRead.deepCopy().apply {
            this.title = noteValidated.title
            this.description = noteValidated.description
            this.email = noteValidated.email
            this.timeReminder = noteValidated.timeReminder
        }
    }
}
