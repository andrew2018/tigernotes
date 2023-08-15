package ru.otus.tigernotes.biz.repo

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import ru.otus.tigernotes.common.TnContext
import ru.otus.tigernotes.common.models.TnState
import ru.otus.tigernotes.common.repo.DbNoteFilterRequest

fun ICorAddExecDsl<TnContext>.repoSearch(title: String) = worker {
    this.title = title
    description = "Поиск заметок в БД по фильтру"
    on { state == TnState.RUNNING }
    handle {
        val request = DbNoteFilterRequest(
            ownerId = noteFilterValidated.ownerId,
            searchTitle = noteFilterValidated.searchTitle,
            dateStart = noteFilterValidated.dateStart,
            dateEnd = noteFilterValidated.dateEnd
        )
        val result = noteRepo.searchNote(request)
        val resultNotes = result.data
        if (result.isSuccess && resultNotes != null) {
            notesRepoDone = resultNotes.toMutableList()
        } else {
            state = TnState.FAILING
            errors.addAll(result.errors)
        }
    }
}
