package ru.otus.tigernotes.validation

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import ru.otus.tigernotes.biz.NoteProcessor
import ru.otus.tigernotes.common.TnContext
import ru.otus.tigernotes.common.TnCorSettings
import ru.otus.tigernotes.common.models.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class BizValidationDeleteTest {

    private val commandDelete = TnCommand.DELETE
    private val processor by lazy { NoteProcessor(TnCorSettings()) }

    @Test
    fun validationIdEmpty() = runTest {
        val ctx = TnContext(
            command = commandDelete,
            state = TnState.NONE,
            workMode = TnWorkMode.TEST,
            note = Note(
                id = NoteId("")
            ),
        )
        processor.exec(ctx)
        assertEquals(1, ctx.errors.size)
        assertEquals(TnState.FAILING, ctx.state)
        val error = ctx.errors.firstOrNull()
        assertEquals("id", error?.field)
        assertContains(error?.message ?: "", "id")
    }

}