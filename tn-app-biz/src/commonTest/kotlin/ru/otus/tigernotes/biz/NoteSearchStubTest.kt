package ru.otus.tigernotes.biz

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import ru.otus.tigernotes.common.TnContext
import ru.otus.tigernotes.common.TnCorSettings
import ru.otus.tigernotes.common.models.*
import ru.otus.tigernotes.common.stubs.TnStubs
import ru.otus.tigernotes.stubs.NoteStub
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.fail

@OptIn(ExperimentalCoroutinesApi::class)
class NoteSearchStubTest {

    private val processor = NoteProcessor(TnCorSettings())
    val filter = NoteFilter(searchTitle = "123-01", dateStart = LocalDate.parse("2023-04-01"), dateEnd = LocalDate.parse("2023-05-01"))

    @Test
    fun search() = runTest {

        val ctx = TnContext(
            command = TnCommand.SEARCH,
            state = TnState.NONE,
            workMode = TnWorkMode.STUB,
            stubCase = TnStubs.SUCCESS,
            noteFilter = filter,
        )
        processor.exec(ctx)
        assertTrue(ctx.notesResponse.size > 1)
        val first = ctx.notesResponse.firstOrNull() ?: fail("Empty response list")
        assertTrue(first.title.contains(filter.searchTitle))
        assertTrue(first.description.contains(filter.searchTitle))
        assertTrue(first.email.contains(filter.searchTitle))
        with (NoteStub.get()) {
            assertEquals(timeCreate, first.timeCreate)
            assertEquals(timeReminder, first.timeReminder)
        }
    }

    @Test
    fun badSearchTitle() = runTest {

        val ctx = TnContext(
            command = TnCommand.SEARCH,
            state = TnState.NONE,
            workMode = TnWorkMode.STUB,
            stubCase = TnStubs.SUCCESS,
            noteFilter = NoteFilter(
                searchTitle = ""
            ),
        )
        processor.exec(ctx)
        assertTrue(ctx.notesResponse.size > 1)
        val first = ctx.notesResponse.firstOrNull() ?: fail("Empty response list")
        assertTrue(first.title.contains(filter.searchTitle))
        assertTrue(first.description.contains(filter.searchTitle))
        assertTrue(first.email.contains(filter.searchTitle))
        with (NoteStub.get()) {
            assertEquals(timeCreate, first.timeCreate)
            assertEquals(timeReminder, first.timeReminder)
        }
    }

    @Test
    fun badId() = runTest {
        val ctx = TnContext(
            command = TnCommand.SEARCH,
            state = TnState.NONE,
            workMode = TnWorkMode.STUB,
            stubCase = TnStubs.BAD_ID,
            noteFilter = filter,
        )
        processor.exec(ctx)
        assertEquals(Note(), ctx.noteResponse)
        assertEquals("id", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
        assertEquals("validation-id", ctx.errors.firstOrNull()?.code)
    }

    @Test
    fun databaseError() = runTest {
        val ctx = TnContext(
            command = TnCommand.SEARCH,
            state = TnState.NONE,
            workMode = TnWorkMode.STUB,
            stubCase = TnStubs.DB_ERROR,
            noteFilter = filter,
        )
        processor.exec(ctx)
        assertEquals(Note(), ctx.noteResponse)
        assertEquals("internal", ctx.errors.firstOrNull()?.group)
        assertEquals("internal-db", ctx.errors.firstOrNull()?.code)
    }

    @Test
    fun badNoCase() = runTest {
        val ctx = TnContext(
            command = TnCommand.SEARCH,
            state = TnState.NONE,
            workMode = TnWorkMode.STUB,
            stubCase = TnStubs.BAD_TITLE,
            noteFilter = filter,
        )
        processor.exec(ctx)
        assertEquals(Note(), ctx.noteResponse)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }
}
