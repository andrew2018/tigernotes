package ru.otus.tigernotes.validation

import NoteRepoStub
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Instant
import ru.otus.tigernotes.biz.NoteProcessor
import ru.otus.tigernotes.common.TnContext
import ru.otus.tigernotes.common.TnCorSettings
import ru.otus.tigernotes.common.models.*
import ru.otus.tigernotes.stubs.NoteStub
import kotlin.test.*

@OptIn(ExperimentalCoroutinesApi::class)
class BizValidationUpdateTest {

    private val commandUpdate = TnCommand.UPDATE
    private val processor by lazy { NoteProcessor(TnCorSettings(repoTest = NoteRepoStub())) }

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
                timeReminder = Instant.parse("2023-07-01T10:00:00Z"),
                lock = NoteLock("123-234-abc-ABC")
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
                timeReminder = Instant.parse("2023-07-01T10:00:00Z"),
                lock = NoteLock("123-234-abc-ABC")
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
                timeReminder = Instant.parse("2023-07-01T10:00:00Z"),
                lock = NoteLock("123-234-abc-ABC")
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
                timeReminder = Instant.parse("2023-07-01T10:00:00Z"),
                lock = NoteLock("123-234-abc-ABC")
            ),
        )
        processor.exec(ctx)
        assertEquals(1, ctx.errors.size)
        assertEquals(TnState.FAILING, ctx.state)
        val error = ctx.errors.firstOrNull()
        assertEquals("description", error?.field)
        assertContains(error?.message ?: "", "description")
    }

}