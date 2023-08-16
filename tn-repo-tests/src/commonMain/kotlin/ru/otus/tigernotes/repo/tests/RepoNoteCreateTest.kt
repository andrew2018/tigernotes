package ru.otus.tigernotes.repo.tests

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.datetime.Instant
import ru.otus.tigernotes.common.models.Note
import ru.otus.tigernotes.common.models.NoteId
import ru.otus.tigernotes.common.models.NoteLock
import ru.otus.tigernotes.common.models.TnUserId
import ru.otus.tigernotes.common.repo.DbNoteRequest
import ru.otus.tigernotes.common.repo.INoteRepository
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals


@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoNoteCreateTest {
    abstract val repo: INoteRepository

    protected open val lockNew: NoteLock = NoteLock("20000000-0000-0000-0000-000000000002")

    private val createObj = Note(
        title = "create object",
        description = "create object description",
        ownerId = TnUserId("owner-123"),
        timeCreate = Instant.parse("2023-04-01T11:00:00Z"),
        email = "create object email",
        timeReminder = Instant.parse("2023-05-01T11:00:00Z")
    )

    @Test
    fun createSuccess() = runRepoTest {
        val result = repo.createNote(DbNoteRequest(createObj))
        val expected = createObj.copy(id = result.data?.id ?: NoteId.NONE)
        assertEquals(true, result.isSuccess)
        assertEquals(expected.title, result.data?.title)
        assertEquals(expected.description, result.data?.description)
        assertEquals(expected.email, result.data?.email)
        assertEquals(expected.timeReminder, result.data?.timeReminder)
        assertNotEquals(NoteId.NONE, result.data?.id)
        assertEquals(emptyList(), result.errors)
        assertEquals(lockNew, result.data?.lock)
    }

    companion object : BaseInitNotes("create") {
        override val initObjects: List<Note> = emptyList()
    }
}
