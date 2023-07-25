package ru.otus.tigernotes.repo.tests

import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.otus.tigernotes.common.models.Note
import ru.otus.tigernotes.common.models.NoteId
import ru.otus.tigernotes.common.models.NoteLock
import ru.otus.tigernotes.common.repo.DbNoteIdRequest
import ru.otus.tigernotes.common.repo.DbNoteRequest
import ru.otus.tigernotes.common.repo.INoteRepository
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoNoteDeleteTest {
    abstract val repo: INoteRepository
    protected open val deleteSucc = initObjects[0]
    protected open val deleteConc = initObjects[1]
    protected open val notFoundId = NoteId("note-repo-delete-notFound")

    @Test
    fun deleteSuccess() = runRepoTest {
        val lockOld = deleteSucc.lock
        val result = repo.deleteNote(DbNoteIdRequest(deleteConc.id, lock = lockOld))

        assertEquals(true, result.isSuccess)
        assertEquals(emptyList(), result.errors)
        assertEquals(lockOld, result.data?.lock)
    }

    @Test
    fun deleteNotFound() = runRepoTest {
        val result = repo.readNote(DbNoteIdRequest(notFoundId, lock = lockOld))

        assertEquals(false, result.isSuccess)
        assertEquals(null, result.data)
        val error = result.errors.find { it.code == "not-found" }
        assertEquals("id", error?.field)
    }

    @Test
    fun deleteConcurrency() = runRepoTest {
        val lockOld = deleteSucc.lock
        val result = repo.deleteNote(DbNoteIdRequest(deleteConc.id, lock = lockBad))

        assertEquals(false, result.isSuccess)
        val error = result.errors.find { it.code == "concurrency" }
        assertEquals("lock", error?.field)
        assertEquals(lockOld, result.data?.lock)
    }

    companion object : BaseInitNotes("delete") {
        override val initObjects: List<Note> = listOf(
            createInitTestModel("delete"),
            createInitTestModel("deleteLock"),
        )
        val notFoundId = NoteId("note-repo-delete-notFound")
    }
}
