package ru.otus.tigernotes.validation

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Clock
import ru.otus.tigernotes.biz.NoteProcessor
import ru.otus.tigernotes.common.TnContext
import ru.otus.tigernotes.common.TnCorSettings
import ru.otus.tigernotes.common.models.*
import ru.otus.tigernotes.stubs.NoteStub
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.time.Duration.Companion.days

@OptIn(ExperimentalCoroutinesApi::class)
class BizValidationUpdateTest {

    private val commandUpdate = TnCommand.UPDATE
    private val processor by lazy { NoteProcessor(TnCorSettings()) }

    @Test
    fun validationIdEmpty() = runTest {
        val ctx = TnContext(
            command = commandUpdate,
            state = TnState.NONE,
            workMode = TnWorkMode.TEST,
            note = Note(
                id = NoteId(""),
                title = "123",
                description = "123",
                email = "test@mail.ru",
                timeReminder = Clock.System.now().plus(1.days)
            ),
        )
        processor.exec(ctx)
        assertEquals(1, ctx.errors.size)
        assertEquals(TnState.FAILING, ctx.state)
        val error = ctx.errors.firstOrNull()
        assertEquals("id", error?.field)
        assertContains(error?.message ?: "", "id")
    }

    @Test
    fun validationTitleCorrect() = runTest {
        val ctx = TnContext(
            command = commandUpdate,
            state = TnState.NONE,
            workMode = TnWorkMode.TEST,
            note = Note(
                id = NoteStub.get().id,
                title = "123",
                description = "123",
                email = "test@mail.ru",
                timeReminder = Clock.System.now().plus(1.days)
            )
        )
        processor.exec(ctx)
        assertEquals(0, ctx.errors.size)
        assertNotEquals(TnState.FAILING, ctx.state)
        assertEquals("123", ctx.noteValidated.title)
    }

    @Test
    fun validationTitleEmpty() = runTest {
        val ctx = TnContext(
            command = commandUpdate,
            state = TnState.NONE,
            workMode = TnWorkMode.TEST,
            note = Note(
                id = NoteStub.get().id,
                title = "",
                description = "123",
                email = "test@mail.ru",
                timeReminder = Clock.System.now().plus(1.days)
            ),
        )
        processor.exec(ctx)
        assertEquals(1, ctx.errors.size)
        assertEquals(TnState.FAILING, ctx.state)
        val error = ctx.errors.firstOrNull()
        assertEquals("title", error?.field)
        assertContains(error?.message ?: "", "title")
    }

    @Test
    fun validationDescriptionEmpty() = runTest {
        val ctx = TnContext(
            command = commandUpdate,
            state = TnState.NONE,
            workMode = TnWorkMode.TEST,
            note = Note(
                id = NoteStub.get().id,
                title = "123",
                description = "",
                email = "test@mail.ru",
                timeReminder = Clock.System.now().plus(1.days)
            ),
        )
        processor.exec(ctx)
        assertEquals(1, ctx.errors.size)
        assertEquals(TnState.FAILING, ctx.state)
        val error = ctx.errors.firstOrNull()
        assertEquals("description", error?.field)
        assertContains(error?.message ?: "", "description")
    }

    @Test
    fun validationEmailEmpty() = runTest {
        val ctx = TnContext(
            command = commandUpdate,
            state = TnState.NONE,
            workMode = TnWorkMode.TEST,
            note = Note(
                id = NoteStub.get().id,
                title = "123",
                description = "123",
                email = "",
                timeReminder = Clock.System.now().plus(1.days)
            ),
        )
        processor.exec(ctx)
        assertEquals(1, ctx.errors.size)
        assertEquals(TnState.FAILING, ctx.state)
        val error = ctx.errors.firstOrNull()
        assertEquals("email", error?.field)
        assertContains(error?.message ?: "", "email")
    }

    @Test
    fun validationEmailNotValid() = runTest {
        val ctx = TnContext(
            command = commandUpdate,
            state = TnState.NONE,
            workMode = TnWorkMode.TEST,
            note = Note(
                id = NoteStub.get().id,
                title = "123",
                description = "123",
                email = "test",
                timeReminder = Clock.System.now().plus(1.days)
            ),
        )
        processor.exec(ctx)
        assertEquals(1, ctx.errors.size)
        assertEquals(TnState.FAILING, ctx.state)
        val error = ctx.errors.firstOrNull()
        assertEquals("email", error?.field)
        assertContains(error?.message ?: "", "email")
    }

    @Test
    fun validationTimeReminderInCorrect() = runTest {
        val ctx = TnContext(
            command = commandUpdate,
            state = TnState.NONE,
            workMode = TnWorkMode.TEST,
            note = Note(
                id = NoteStub.get().id,
                title = "123",
                description = "123",
                email = "test@mail.ru",
                timeReminder = Clock.System.now().minus(1.days)
            ),
        )
        processor.exec(ctx)
        assertEquals(1, ctx.errors.size)
        assertEquals(TnState.FAILING, ctx.state)
        val error = ctx.errors.firstOrNull()
        assertEquals("timeReminder", error?.field)
        assertContains(error?.message ?: "", "timeReminder")
    }

}