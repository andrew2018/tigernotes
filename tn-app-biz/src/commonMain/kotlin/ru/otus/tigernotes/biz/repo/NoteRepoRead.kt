package ru.otus.tigernotes.biz.repo

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import ru.otus.tigernotes.common.TnContext
import ru.otus.tigernotes.common.models.TnState
import ru.otus.tigernotes.common.repo.DbNoteIdRequest

fun ICorAddExecDsl<TnContext>.repoRead(title: String) = worker {
    this.title = title
    description = "Чтение заметки из БД"
    on { state == TnState.RUNNING }
    handle {
        val request = DbNoteIdRequest(noteValidated)
        val result = noteRepo.readNote(request)
        val resultNote = result.data
        if (result.isSuccess && resultNote != null) {
            noteRepoRead = resultNote
        } else {
            state = TnState.FAILING
            errors.addAll(result.errors)
        }
    }
}
