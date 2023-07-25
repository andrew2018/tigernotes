package ru.otus.tigernotes.common.repo

import ru.otus.tigernotes.common.helpers.errorEmptyId as tnErrorEmptyId
import ru.otus.tigernotes.common.helpers.errorNotFound as tnErrorNotFound
import ru.otus.tigernotes.common.helpers.errorRepoConcurrency
import ru.otus.tigernotes.common.models.Note
import ru.otus.tigernotes.common.models.NoteLock
import ru.otus.tigernotes.common.models.TnError

data class DbNoteResponse(
    override val data: Note?,
    override val isSuccess: Boolean,
    override val errors: List<TnError> = emptyList()
): IDbResponse<Note> {

    companion object {
        val MOCK_SUCCESS_EMPTY = DbNoteResponse(null, true)
        fun success(result: Note) = DbNoteResponse(result, true)
        fun error(errors: List<TnError>, data: Note? = null) = DbNoteResponse(data, false, errors)
        fun error(error: TnError, data: Note? = null) = DbNoteResponse(data, false, listOf(error))

        val errorEmptyId = error(tnErrorEmptyId)

        fun errorConcurrent(lock: NoteLock, note: Note?) = error(
            errorRepoConcurrency(lock, note?.lock?.let { NoteLock(it.asString()) }),
            note
        )

        val errorNotFound = error(tnErrorNotFound)
    }
}
