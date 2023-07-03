package ru.otus.tigernotes.mappers

import kotlinx.datetime.Instant
import ru.otus.tigernotes.common.TnContext
import ru.otus.tigernotes.common.models.*
import ru.otus.tigernotes.mappers.log.toLog
import kotlin.test.Test
import kotlin.test.assertEquals

class MapperLogTest {

    @Test
    fun fromContext() {
        val context = TnContext(
            requestId = TnRequestId("1234"),
            command = TnCommand.CREATE,
            noteResponse = Note(
                title = "title",
                description = "desc",
                timeCreate = Instant.parse("2023-04-01T11:00:00Z"),
                email = "email",
                timeReminder = Instant.parse("2023-04-02T11:00:00Z")
            ),
            errors = mutableListOf(
                TnError(
                    code = "err",
                    group = "request",
                    field = "title",
                    message = "wrong title",
                )
            ),
            state = TnState.RUNNING,
        )

        val log = context.toLog("test-id")

        assertEquals("test-id", log.logId)
        assertEquals("ok-tigernotes", log.source)
        assertEquals("1234", log.note?.requestId)
        val error = log.errors?.firstOrNull()
        assertEquals("wrong title", error?.message)
        assertEquals("ERROR", error?.level)
    }
}
