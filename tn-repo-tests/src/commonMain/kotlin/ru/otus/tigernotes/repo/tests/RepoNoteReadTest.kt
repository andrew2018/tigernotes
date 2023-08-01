package ru.otus.tigernotes.repo.tests

import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.otus.tigernotes.common.models.Note
import ru.otus.tigernotes.common.models.NoteId
import ru.otus.tigernotes.common.repo.DbNoteIdRequest
import ru.otus.tigernotes.common.repo.INoteRepository
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoNoteReadTest {
    abstract val repo: INoteRepository
    protected open val readSucc = initObjects[0]

    @Test
    fun readSuccess() = runRepoTest {
        val result = repo.readNote(DbNoteIdRequest(readSucc.id))

        assertEquals(true, result.isSuccess)
        assertEquals(readSucc.id, result.data?.id)
        assertEquals(readSucc.title, result.data?.title)
        assertEquals(readSucc.description, result.data?.description)
        assertEquals(readSucc.email, result.data?.email)
        assertEquals(readSucc.timeReminder, result.data?.timeReminder)
        assertEquals(emptyList(), result.errors)
    }

    @Test
    fun readNotFound() = runRepoTest {
        val result = repo.readNote(DbNoteIdRequest(notFoundId))

        assertEquals(false, result.isSuccess)
        assertEquals(null, result.data)
        val error = result.errors.find { it.code == "not-found" }
        assertEquals("id", error?.field)
    }

    companion object : BaseInitNotes("delete") {
        override val initObjects: List<Note> = listOf(
            createInitTestModel("read")
        )

        val notFoundId = NoteId("ad-repo-read-notFound")

    }
}
