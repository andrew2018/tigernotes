package ru.otus.tigernotes.repo.tests

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.datetime.*
import ru.otus.tigernotes.common.models.Note
import ru.otus.tigernotes.common.repo.DbNoteFilterRequest
import ru.otus.tigernotes.common.repo.INoteRepository
import kotlin.test.*
import kotlin.time.Duration.Companion.days

@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoNoteSearchTest {
    abstract val repo: INoteRepository

    protected open val initializedObjects: List<Note> = initObjects

    @Test
    fun searchByTitle() = runRepoTest {
        val result = repo.searchNote(DbNoteFilterRequest(searchTitle = "note1"))
        assertEquals(true, result.isSuccess)
        val expected = listOf(initializedObjects[0]).sortedBy { it.id.asString() }
        assertEquals(expected.size, 1)
        assertEquals(expected[0].id, result.data?.sortedBy { it.id.asString() }?.get(0)?.id)
        assertEquals(emptyList(), result.errors)
    }

    @Test
    fun searchByDate() = runRepoTest {
        val result = repo.searchNote(DbNoteFilterRequest(
            dateStart = Clock.System.now().minus(1.days).toLocalDateTime(TimeZone.UTC).date,
            dateEnd = Clock.System.now().plus(1.days).toLocalDateTime(TimeZone.UTC).date))
        assertEquals(true, result.isSuccess)
        val expected = listOf(initializedObjects[0], initializedObjects[1]).sortedBy { it.id.asString() }
        assertEquals(expected.size, 2)
        assertEquals(expected[0].id, result.data?.sortedBy { it.id.asString() }?.get(0)?.id)
        assertEquals(expected[1].id, result.data?.sortedBy { it.id.asString() }?.get(1)?.id)
        assertEquals(emptyList(), result.errors)
    }

    companion object: BaseInitNotes("search") {
        override val initObjects: List<Note> = listOf(
            createInitTestModel("note1"),
            createInitTestModel("note2")
        )
    }
}
