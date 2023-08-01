package ru.otus.tigernotes.common.repo

interface INoteRepository {
    suspend fun createNote(rq: DbNoteRequest): DbNoteResponse
    suspend fun readNote(rq: DbNoteIdRequest): DbNoteResponse
    suspend fun updateNote(rq: DbNoteRequest): DbNoteResponse
    suspend fun deleteNote(rq: DbNoteIdRequest): DbNoteResponse
    suspend fun searchNote(rq: DbNoteFilterRequest): DbNotesResponse
    companion object {
        val NONE = object : INoteRepository {
            override suspend fun createNote(rq: DbNoteRequest): DbNoteResponse {
                TODO("Not yet implemented")
            }

            override suspend fun readNote(rq: DbNoteIdRequest): DbNoteResponse {
                TODO("Not yet implemented")
            }

            override suspend fun updateNote(rq: DbNoteRequest): DbNoteResponse {
                TODO("Not yet implemented")
            }

            override suspend fun deleteNote(rq: DbNoteIdRequest): DbNoteResponse {
                TODO("Not yet implemented")
            }

            override suspend fun searchNote(rq: DbNoteFilterRequest): DbNotesResponse {
                TODO("Not yet implemented")
            }
        }
    }
}
