package ru.otus.tigernotes.validation

import NoteRepoStub
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import ru.otus.tigernotes.biz.NoteProcessor
import ru.otus.tigernotes.common.TnContext
import ru.otus.tigernotes.common.TnCorSettings
import ru.otus.tigernotes.common.models.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

@OptIn(ExperimentalCoroutinesApi::class)
class BizValidationSearchTest {

    private val commandSearch = TnCommand.SEARCH
    private val processor by lazy { NoteProcessor(TnCorSettings(repoTest = NoteRepoStub())) }

    @Test
    fun validationSearchTitleCorrect() = runTest {
        val ctx = TnContext(
            command = commandSearch,
            state = TnState.NONE,
            workMode = TnWorkMode.TEST,
            noteFilter = NoteFilter(
                searchTitle = "123",
                dateStart = LocalDate.parse("2023-04-01"),
                dateEnd = LocalDate.parse("2023-05-01")
            )
        )
        processor.exec(ctx)
        assertEquals(0, ctx.errors.size)
        assertNotEquals(TnState.FAILING, ctx.state)
        assertEquals("123", ctx.noteFilterValidated.searchTitle)
    }

}