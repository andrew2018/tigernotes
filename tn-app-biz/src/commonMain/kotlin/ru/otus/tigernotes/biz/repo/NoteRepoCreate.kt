package ru.otus.tigernotes.biz.repo

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import ru.otus.tigernotes.common.TnContext
import ru.otus.tigernotes.common.models.TnState
import ru.otus.tigernotes.common.repo.DbNoteRequest

fun ICorAddExecDsl<TnContext>.repoCreate(title: String) = worker {
    this.title = title
    description = "Добавление заметки в БД"
    on { state == TnState.RUNNING }
    handle {
        val request = DbNoteRequest(noteRepoPrepare)
        val result = noteRepo.createNote(request)
        val resultNote = result.data
        if (result.isSuccess && resultNote != null) {
            noteRepoDone = resultNote
        } else {
            state = TnState.FAILING
            errors.addAll(result.errors)
        }
    }
}
