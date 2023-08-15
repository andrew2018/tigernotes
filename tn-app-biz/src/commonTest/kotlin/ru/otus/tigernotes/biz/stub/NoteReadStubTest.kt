package ru.otus.tigernotes.biz.stub

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import ru.otus.tigernotes.biz.NoteProcessor
import ru.otus.tigernotes.common.TnContext
import ru.otus.tigernotes.common.TnCorSettings
import ru.otus.tigernotes.common.models.*
import ru.otus.tigernotes.common.stubs.TnStubs
import ru.otus.tigernotes.stubs.NoteStub
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class NoteReadStubTest {

    private val processor = NoteProcessor(TnCorSettings())
    val id = NoteId("123")

    @Test
    fun read() = runTest {

        val ctx = TnContext(
            command = TnCommand.READ,
            state = TnState.NONE,
            workMode = TnWorkMode.STUB,
            stubCase = TnStubs.SUCCESS,
            note = Note(
                id = id,
            ),
        )
        processor.exec(ctx)
        with (NoteStub.get()) {
            assertEquals(id, ctx.noteResponse.id)
            assertEquals(title, ctx.noteResponse.title)
            assertEquals(description, ctx.noteResponse.description)
            assertEquals(timeCreate, ctx.noteResponse.timeCreate)
            assertEquals(email, ctx.noteResponse.email)
            assertEquals(timeReminder, ctx.noteResponse.timeReminder)
        }
    }

    @Test
    fun badId() = runTest {
        val ctx = TnContext(
            command = TnCommand.READ,
            state = TnState.NONE,
            workMode = TnWorkMode.STUB,
            stubCase = TnStubs.BAD_ID,
            note = Note(),
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
            command = TnCommand.READ,
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
            command = TnCommand.READ,
            state = TnState.NONE,
            workMode = TnWorkMode.STUB,
            stubCase = TnStubs.BAD_TITLE,
            note = Note(
                id = id,
            ),
        )
        processor.exec(ctx)
        assertEquals(Note(), ctx.noteResponse)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }
}
