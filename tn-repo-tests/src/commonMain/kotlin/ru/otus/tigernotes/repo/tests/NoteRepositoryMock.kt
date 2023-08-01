package ru.otus.tigernotes.repo.tests

import ru.otus.tigernotes.common.repo.*

class NoteRepositoryMock(
    private val invokeCreateAd: (DbNoteRequest) -> DbNoteResponse = { DbNoteResponse.MOCK_SUCCESS_EMPTY },
    private val invokeReadAd: (DbNoteIdRequest) -> DbNoteResponse = { DbNoteResponse.MOCK_SUCCESS_EMPTY },
    private val invokeUpdateAd: (DbNoteRequest) -> DbNoteResponse = { DbNoteResponse.MOCK_SUCCESS_EMPTY },
    private val invokeDeleteAd: (DbNoteIdRequest) -> DbNoteResponse = { DbNoteResponse.MOCK_SUCCESS_EMPTY },
    private val invokeSearchAd: (DbNoteFilterRequest) -> DbNotesResponse = { DbNotesResponse.MOCK_SUCCESS_EMPTY },
): INoteRepository {
    override suspend fun createNote(rq: DbNoteRequest): DbNoteResponse {
        return invokeCreateAd(rq)
    }

    override suspend fun readNote(rq: DbNoteIdRequest): DbNoteResponse {
        return invokeReadAd(rq)
    }

    override suspend fun updateNote(rq: DbNoteRequest): DbNoteResponse {
        return invokeUpdateAd(rq)
    }

    override suspend fun deleteNote(rq: DbNoteIdRequest): DbNoteResponse {
        return invokeDeleteAd(rq)
    }

    override suspend fun searchNote(rq: DbNoteFilterRequest): DbNotesResponse {
        return invokeSearchAd(rq)
    }
}
