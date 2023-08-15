package ru.otus.tigernotes.biz.validation

import NoteRepoStub
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import ru.otus.tigernotes.biz.NoteProcessor
import ru.otus.tigernotes.biz.addTestPrincipal
import ru.otus.tigernotes.common.TnContext
import ru.otus.tigernotes.common.TnCorSettings
import ru.otus.tigernotes.common.models.*
import ru.otus.tigernotes.stubs.NoteStub
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class BizValidationDeleteTest {

    private val commandDelete = TnCommand.DELETE
    private val processor by lazy { NoteProcessor(TnCorSettings(repoTest = NoteRepoStub())) }

    @Test
    fun validationIdEmpty() = runTest {
        val ctx = TnContext(
            command = commandDelete,
            state = TnState.NONE,
            workMode = TnWorkMode.TEST,
            note = NoteStub.prepareResult {
                id = NoteId("")
                lock = NoteLock("123-234-abc-ABC")
            },
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