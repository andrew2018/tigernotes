package ru.otus.tigernotes.repo.tests

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.datetime.Instant
import ru.otus.tigernotes.common.models.Note
import ru.otus.tigernotes.common.models.NoteId
import ru.otus.tigernotes.common.models.NoteLock
import ru.otus.tigernotes.common.repo.DbNoteRequest
import ru.otus.tigernotes.common.repo.INoteRepository
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoNoteUpdateTest {
    abstract val repo: INoteRepository
    protected open val updateSucc = initObjects[0]
    protected open val updateConc = initObjects[1]
    protected val updateIdNotFound = NoteId("note-repo-update-not-found")
    protected val lockBad = NoteLock("20000000-0000-0000-0000-000000000009")
    protected val lockNew = NoteLock("20000000-0000-0000-0000-000000000002")

    private val reqUpdateSucc by lazy {
        Note(
            id = updateSucc.id,
            title = "update object",
            description = "update object description",
            timeCreate = Instant.parse("2023-05-01T11:00:00Z"),
            email = "update object email",
            timeReminder = Instant.parse("2023-07-01T11:00:00Z"),
            lock = initObjects.first().lock,
        )
    }
    private val reqUpdateNotFound = Note(
        id = updateIdNotFound,
        title = "update object not found",
        description = "update object not found description",
        timeCreate = Instant.parse("2023-05-01T11:00:00Z"),
        email = "update object not found email",
        timeReminder = Instant.parse("2023-07-01T11:00:00Z"),
        lock = initObjects.first().lock
    )
    private val reqUpdateConc by lazy {
        Note(
            id = updateConc.id,
            title = "update object not found",
            description = "update object not found description",
            timeCreate = Instant.parse("2023-05-01T11:00:00Z"),
            email = "update object not found email",
            timeReminder = Instant.parse("2023-07-01T11:00:00Z"),
            lock = lockBad,
        )
    }

    @Test
    fun updateSuccess() = runRepoTest {
        val result = repo.updateNote(DbNoteRequest(reqUpdateSucc))
        assertEquals(true, result.isSuccess)
        assertEquals(reqUpdateSucc.id, result.data?.id)
        assertEquals(reqUpdateSucc.title, result.data?.title)
        assertEquals(reqUpdateSucc.description, result.data?.description)
        assertEquals(reqUpdateSucc.email, result.data?.email)
        assertEquals(reqUpdateSucc.timeReminder, result.data?.timeReminder)
        assertEquals(emptyList(), result.errors)
    }

    @Test
    fun updateNotFound() = runRepoTest {
        val result = repo.updateNote(DbNoteRequest(reqUpdateNotFound))
        assertEquals(false, result.isSuccess)
        assertEquals(null, result.data)
        val error = result.errors.find { it.code == "not-found" }
        assertEquals("id", error?.field)
    }

    @Test
    fun updateConcurrencyError() = runRepoTest {
        val result = repo.updateNote(DbNoteRequest(reqUpdateConc))
        assertEquals(false, result.isSuccess)
        val error = result.errors.find { it.code == "concurrency" }
        assertEquals("lock", error?.field)
        assertEquals(updateConc.id, result.data?.id)
        assertEquals(updateConc.title, result.data?.title)
        assertEquals(updateConc.description, result.data?.description)
        assertEquals(updateConc.email, result.data?.email)
        assertEquals(updateConc.timeReminder, result.data?.timeReminder)
    }

    companion object : BaseInitNotes("update") {
        override val initObjects: List<Note> = listOf(
            createInitTestModel("update"),
            createInitTestModel("updateConc"),
        )
    }
}
