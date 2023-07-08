package ru.otus.tigernotes.biz

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import ru.otus.tigernotes.common.TnContext
import ru.otus.tigernotes.common.TnCorSettings
import ru.otus.tigernotes.common.models.*
import ru.otus.tigernotes.common.stubs.TnStubs
import ru.otus.tigernotes.stubs.NoteStub
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class NoteDeleteStubTest {

    private val processor = NoteProcessor(TnCorSettings())
    val id = NoteId("123")

    @Test
    fun delete() = runTest {

        val ctx = TnContext(
            command = TnCommand.DELETE,
            state = TnState.NONE,
            workMode = TnWorkMode.STUB,
            stubCase = TnStubs.SUCCESS,
            note = Note(
                id = id,
            ),
        )
        processor.exec(ctx)

        val stub = NoteStub.get()
        assertEquals(stub.id, ctx.noteResponse.id)
        assertEquals(stub.title, ctx.noteResponse.title)
        assertEquals(stub.description, ctx.noteResponse.description)
        assertEquals(stub.timeCreate, ctx.noteResponse.timeCreate)
        assertEquals(stub.email, ctx.noteResponse.email)
        assertEquals(stub.timeReminder, ctx.noteResponse.timeReminder)
    }

    @Test
    fun badId() = runTest {
        val ctx = TnContext(
            command = TnCommand.DELETE,
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
            command = TnCommand.DELETE,
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
            command = TnCommand.DELETE,
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
