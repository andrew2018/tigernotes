package ru.otus.tigernotes.common.repo

import ru.otus.tigernotes.common.models.Note
import ru.otus.tigernotes.common.models.TnError

data class DbNotesResponse(
    override val data: List<Note>?,
    override val isSuccess: Boolean,
    override val errors: List<TnError> = emptyList(),
): IDbResponse<List<Note>> {

    companion object {
        val MOCK_SUCCESS_EMPTY = DbNotesResponse(emptyList(), true)
        fun success(result: List<Note>) = DbNotesResponse(result, true)
        fun error(errors: List<TnError>) = DbNotesResponse(null, false, errors)
        fun error(error: TnError) = DbNotesResponse(null, false, listOf(error))
    }
}
