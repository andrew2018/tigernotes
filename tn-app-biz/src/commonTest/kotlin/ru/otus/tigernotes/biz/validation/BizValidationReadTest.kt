package ru.otus.tigernotes.biz.validation

import NoteRepoStub
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Instant
import ru.otus.tigernotes.biz.NoteProcessor
import ru.otus.tigernotes.biz.addTestPrincipal
import ru.otus.tigernotes.common.TnContext
import ru.otus.tigernotes.common.TnCorSettings
import ru.otus.tigernotes.common.models.*
import ru.otus.tigernotes.stubs.NoteStub
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

@OptIn(ExperimentalCoroutinesApi::class)
class BizValidationReadTest {

    private val commandRead = TnCommand.READ
    private val processor by lazy { NoteProcessor(TnCorSettings(repoTest = NoteRepoStub())) }

    @Test
    fun validationTitleCorrect() = runTest {
        val ctx = TnContext(
            command = commandRead,
            state = TnState.NONE,
            workMode = TnWorkMode.TEST,
            note = Note(
                id = NoteStub.get().id,
                title = "123",
                description = "123",
                email = "test@mail.ru",
                timeReminder = Instant.parse("2023-07-01T10:00:00Z")
            )
        )
        ctx.addTestPrincipal(NoteStub.get().ownerId)
        processor.exec(ctx)
        assertEquals(0, ctx.errors.size)
        assertNotEquals(TnState.FAILING, ctx.state)
        assertEquals("123", ctx.noteValidated.title)
    }

    @Test
    fun validationIdEmpty() = runTest {
        val ctx = TnContext(
            command = commandRead,
            state = TnState.NONE,
            workMode = TnWorkMode.TEST,
            note = Note(
                id = NoteId("")
            ),
        )
        ctx.addTestPrincipal(NoteStub.get().ownerId)
        processor.exec(ctx)
        assertEquals(1, ctx.errors.size)
        assertEquals(TnState.FAILING, ctx.state)
        val error = ctx.errors.firstOrNull()
        assertEquals("id", error?.field)
        assertContains(error?.message ?: "", "id")
    }

}