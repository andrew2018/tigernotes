package ru.otus.tigernotes.biz.repo

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import ru.otus.tigernotes.common.TnContext
import ru.otus.tigernotes.common.models.TnState
import ru.otus.tigernotes.common.repo.DbNoteRequest

fun ICorAddExecDsl<TnContext>.repoUpdate(title: String) = worker {
    this.title = title
    on { state == TnState.RUNNING }
    handle {
        val request = DbNoteRequest(noteRepoPrepare)
        val result = noteRepo.updateNote(request)
        val resultNote = result.data
        if (result.isSuccess && resultNote != null) {
            noteRepoDone = resultNote
        } else {
            state = TnState.FAILING
            errors.addAll(result.errors)
            noteRepoDone
        }
    }
}
