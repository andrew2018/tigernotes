package ru.otus.tigernotes.biz.stub

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Instant
import ru.otus.tigernotes.biz.NoteProcessor
import ru.otus.tigernotes.common.NONE
import ru.otus.tigernotes.common.TnContext
import ru.otus.tigernotes.common.TnCorSettings
import ru.otus.tigernotes.common.models.*
import ru.otus.tigernotes.common.stubs.TnStubs
import ru.otus.tigernotes.stubs.NoteStub
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class NoteCreateStubTest {

    private val processor = NoteProcessor(TnCorSettings())
    val id = NoteId("123")
    val title = "title 123"
    val description = "desc 123"
    val timeCreate = Instant.parse("2023-04-01T10:00:00Z")
    val email = "email 123"
    val timeReminder = Instant.parse("2023-04-01T11:00:00Z")

    @Test
    fun create() = runTest {

        val ctx = TnContext(
            command = TnCommand.CREATE,
            state = TnState.NONE,
            workMode = TnWorkMode.STUB,
            stubCase = TnStubs.SUCCESS,
            note = Note(
                id = id,
                title = title,
                description = description,
                timeCreate = timeCreate,
                email = email,
                timeReminder = timeReminder,
            ),
        )
        processor.exec(ctx)
        assertEquals(NoteStub.get().id, ctx.noteResponse.id)
        assertEquals(title, ctx.noteResponse.title)
        assertEquals(description, ctx.noteResponse.description)
        assertEquals(timeCreate, ctx.noteResponse.timeCreate)
        assertEquals(email, ctx.noteResponse.email)
        assertEquals(timeReminder, ctx.noteResponse.timeReminder)
    }

    @Test
    fun badTitle() = runTest {
        val ctx = TnContext(
            command = TnCommand.CREATE,
            state = TnState.NONE,
            workMode = TnWorkMode.STUB,
            stubCase = TnStubs.BAD_TITLE,
            note = Note(
                id = id,
                title = "",
                description = description,
                timeCreate = timeCreate,
                email = email,
                timeReminder = timeReminder,
            ),
        )
        processor.exec(ctx)
        assertEquals(Note(), ctx.noteResponse)
        assertEquals("title", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
        assertEquals("validation-title", ctx.errors.firstOrNull()?.code)
    }
    @Test
    fun badDescription() = runTest {
        val ctx = TnContext(
            command = TnCommand.CREATE,
            state = TnState.NONE,
            workMode = TnWorkMode.STUB,
            stubCase = TnStubs.BAD_DESCRIPTION,
            note = Note(
                id = id,
                title = title,
                description = "",
                timeCreate = timeCreate,
                email = email,
                timeReminder = timeReminder,
            ),
        )
        processor.exec(ctx)
        assertEquals(Note(), ctx.noteResponse)
        assertEquals("description", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
        assertEquals("validation-description", ctx.errors.firstOrNull()?.code)
    }
    @Test
    fun badEmail() = runTest {
        val ctx = TnContext(
            command = TnCommand.CREATE,
            state = TnState.NONE,
            workMode = TnWorkMode.STUB,
            stubCase = TnStubs.BAD_EMAIL,
            note = Note(
                id = id,
                title = title,
                description = description,
                timeCreate = timeCreate,
                email = "",
                timeReminder = timeReminder,
            ),
        )
        processor.exec(ctx)
        assertEquals(Note(), ctx.noteResponse)
        assertEquals("email", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
        assertEquals("validation-email", ctx.errors.firstOrNull()?.code)
    }
    @Test
    fun badTimeReminder() = runTest {
        val ctx = TnContext(
            command = TnCommand.CREATE,
            state = TnState.NONE,
            workMode = TnWorkMode.STUB,
            stubCase = TnStubs.BAD_TIME_REMINDER,
            note = Note(
                id = id,
                title = title,
                description = description,
                timeCreate = timeCreate,
                email = email,
                timeReminder = Instant.NONE,
            ),
        )
        processor.exec(ctx)
        assertEquals(Note(), ctx.noteResponse)
        assertEquals("email", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
        assertEquals("validation-timeReminder", ctx.errors.firstOrNull()?.code)
    }

    @Test
    fun databaseError() = runTest {
        val ctx = TnContext(
            command = TnCommand.CREATE,
            state = TnState.NONE,
            workMode = TnWorkMode.STUB,
            stubCase = TnStubs.DB_ERROR,
            note = Note(
                id = id,
            ),
        )
        processor.exec(ctx)
        assertEquals(Note(), ctx.noteResponse)
        assertEquals("internal", ctx.errors.firstOrNull()?.group)
        assertEquals("internal-db", ctx.errors.firstOrNull()?.code)
    }

    @Test
    fun badNoCase() = runTest {
        val ctx = TnContext(
            command = TnCommand.CREATE,
            state = TnState.NONE,
            workMode = TnWorkMode.STUB,
            stubCase = TnStubs.BAD_ID,
            note = Note(
                id = id,
                title = title,
                description = "",
                timeCreate = timeCreate,
                email = email,
                timeReminder = timeReminder,
            ),
        )
        processor.exec(ctx)
        assertEquals(Note(), ctx.noteResponse)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }
}
