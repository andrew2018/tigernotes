package ru.otus.tigernotes.biz.repo

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import ru.otus.tigernotes.common.TnContext
import ru.otus.tigernotes.common.models.TnState
import ru.otus.tigernotes.common.repo.DbNoteIdRequest

fun ICorAddExecDsl<TnContext>.repoDelete(title: String) = worker {
    this.title = title
    description = "Удаление заметки из БД по ID"
    on { state == TnState.RUNNING }
    handle {
        val request = DbNoteIdRequest(noteRepoPrepare)
        val result = noteRepo.deleteNote(request)
        if (!result.isSuccess) {
            state = TnState.FAILING
            errors.addAll(result.errors)
        }
        noteRepoDone = noteRepoRead
    }
}
