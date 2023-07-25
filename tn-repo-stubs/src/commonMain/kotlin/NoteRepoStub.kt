import kotlinx.datetime.LocalDate
import ru.otus.tigernotes.common.repo.*
import ru.otus.tigernotes.stubs.NoteStub

class NoteRepoStub() : INoteRepository {
    override suspend fun createNote(rq: DbNoteRequest): DbNoteResponse {
        return DbNoteResponse(
            data = NoteStub.prepareResult {  },
            isSuccess = true,
        )
    }

    override suspend fun readNote(rq: DbNoteIdRequest): DbNoteResponse {
        return DbNoteResponse(
            data = NoteStub.prepareResult {  },
            isSuccess = true,
        )
    }

    override suspend fun updateNote(rq: DbNoteRequest): DbNoteResponse {
        return DbNoteResponse(
            data = NoteStub.prepareResult {  },
            isSuccess = true,
        )
    }

    override suspend fun deleteNote(rq: DbNoteIdRequest): DbNoteResponse {
        return DbNoteResponse(
            data = NoteStub.prepareResult {  },
            isSuccess = true,
        )
    }

    override suspend fun searchNote(rq: DbNoteFilterRequest): DbNotesResponse {
        return DbNotesResponse(
            data = NoteStub.prepareSearchList("", LocalDate.parse("2023-05-01"), LocalDate.parse("2023-05-01")),
            isSuccess = true,
        )
    }
}
