package ru.otus.tigernotes.mappers

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import ru.otus.tigernotes.common.TnContext
import ru.otus.tigernotes.common.models.*
import ru.otus.tigernotes.common.stubs.TnStubs
import ru.otus.tigernotes.api.v2.models.*
import kotlin.test.Test
import kotlin.test.assertEquals

class MapperTest {
    @Test
    fun fromTransportCreateRequest() {
        val req = NoteCreateRequest(
            requestId = "1234",
            debug = NoteDebug(
                mode = NoteRequestDebugMode.STUB,
                stub = NoteRequestDebugStubs.SUCCESS,
            ),
            noteCreate = NoteObject(
                title = "title",
                description = "desc",
                email = "email",
                timeReminder = "2023-04-01T12:00:00Z"
            ),
        )

        val context = TnContext()
        context.fromTransport(req)

        assertEquals("1234", req.requestId)
        assertEquals(TnStubs.SUCCESS, context.stubCase)
        assertEquals(TnWorkMode.STUB, context.workMode)
        assertEquals("title", context.note.title)
        assertEquals("desc", context.note.description)
        assertEquals("email", context.note.email)
        assertEquals(Instant.parse("2023-04-01T12:00:00Z"), context.note.timeReminder)
    }

    @Test
    fun toTransportCreateResponse() {
        val context = TnContext(
            requestId = TnRequestId("1234"),
            command = TnCommand.CREATE,
            noteResponse = Note(
                title = "title",
                description = "desc",
                email = "email",
                timeReminder = Instant.parse("2023-04-01T12:00:00Z")
            ),
            errors = mutableListOf(
                TnError(
                    code = "err",
                    group = "request",
                    field = "title",
                    message = "wrong title",
                )
            ),
            state = TnState.FAILING,
        )

        val req = context.toTransportNote() as NoteCreateResponse

        assertEquals("1234", req.requestId)
        assertEquals("title", req.note?.title)
        assertEquals("desc", req.note?.description)
        assertEquals("email", req.note?.email)
        assertEquals("2023-04-01T12:00:00Z", req.note?.timeReminder)
        assertEquals(1, req.errors?.size)
        assertEquals("err", req.errors?.firstOrNull()?.code)
        assertEquals("request", req.errors?.firstOrNull()?.group)
        assertEquals("title", req.errors?.firstOrNull()?.field)
        assertEquals("wrong title", req.errors?.firstOrNull()?.message)
        assertEquals(ResponseResult.ERROR, req.result)
    }

    @Test
    fun fromTransportReadRequest() {
        val req = NoteReadRequest(
            requestId = "1234",
            debug = NoteDebug(
                mode = NoteRequestDebugMode.STUB,
                stub = NoteRequestDebugStubs.SUCCESS,
            ),
            noteId = "456"
        )

        val context = TnContext()
        context.fromTransport(req)

        assertEquals(TnStubs.SUCCESS, context.stubCase)
        assertEquals(TnWorkMode.STUB, context.workMode)
        assertEquals("456", context.note.id.asString())
    }

    @Test
    fun toTransportReadResponse() {
        val context = TnContext(
            requestId = TnRequestId("1234"),
            command = TnCommand.READ,
            noteResponse = Note(
                id = NoteId("456"),
                title = "title",
                description = "desc",
                email = "email",
                timeReminder = Instant.parse("2023-04-01T12:00:00Z")
            ),
            state = TnState.RUNNING,
        )

        val req = context.toTransportNote() as NoteReadResponse

        assertEquals("1234", req.requestId)
        assertEquals("456", req.note?.id)
        assertEquals("title", req.note?.title)
        assertEquals("desc", req.note?.description)
        assertEquals("email", req.note?.email)
        assertEquals("2023-04-01T12:00:00Z", req.note?.timeReminder)
        assertEquals(ResponseResult.SUCCESS, req.result)
    }

    @Test
    fun fromTransportUpdateRequest() {
        val req = NoteUpdateRequest(
            requestId = "1234",
            debug = NoteDebug(
                mode = NoteRequestDebugMode.STUB,
                stub = NoteRequestDebugStubs.SUCCESS,
            ),
            noteUpdate = NoteUpdateObject(
                id = "456",
                title = "title",
                description = "desc",
                email = "email",
                timeReminder = "2023-04-01T12:00:00Z"
            ),
        )

        val context = TnContext()
        context.fromTransport(req)

        assertEquals("1234", req.requestId)
        assertEquals(TnStubs.SUCCESS, context.stubCase)
        assertEquals(TnWorkMode.STUB, context.workMode)
        assertEquals("456", context.note.id.asString())
        assertEquals("title", context.note.title)
        assertEquals("desc", context.note.description)
        assertEquals("email", context.note.email)
        assertEquals(Instant.parse("2023-04-01T12:00:00Z"), context.note.timeReminder)
    }

    @Test
    fun toTransportUpdateResponse() {
        val context = TnContext(
            requestId = TnRequestId("1234"),
            command = TnCommand.UPDATE,
            noteResponse = Note(
                id = NoteId("456"),
                title = "title",
                description = "desc",
                email = "email",
                timeReminder = Instant.parse("2023-04-01T12:00:00Z")
            ),
            state = TnState.RUNNING,
        )

        val req = context.toTransportNote() as NoteUpdateResponse

        assertEquals("1234", req.requestId)
        assertEquals("456", req.note?.id)
        assertEquals("title", req.note?.title)
        assertEquals("desc", req.note?.description)
        assertEquals("email", req.note?.email)
        assertEquals("2023-04-01T12:00:00Z", req.note?.timeReminder)
        assertEquals(ResponseResult.SUCCESS, req.result)
    }

    @Test
    fun fromTransportDeleteRequest() {
        val req = NoteDeleteRequest(
            requestId = "1234",
            debug = NoteDebug(
                mode = NoteRequestDebugMode.STUB,
                stub = NoteRequestDebugStubs.SUCCESS,
            ),
            noteId = "456"
        )

        val context = TnContext()
        context.fromTransport(req)

        assertEquals(TnStubs.SUCCESS, context.stubCase)
        assertEquals(TnWorkMode.STUB, context.workMode)
        assertEquals("456", context.note.id.asString())
    }

    @Test
    fun toTransportDeleteResponse() {
        val context = TnContext(
            requestId = TnRequestId("1234"),
            command = TnCommand.DELETE,
            noteResponse = Note(
                id = NoteId("456"),
                title = "title",
                description = "desc",
                email = "email",
                timeReminder = Instant.parse("2023-04-01T12:00:00Z")
            ),
            state = TnState.RUNNING,
        )

        val req = context.toTransportNote() as NoteDeleteResponse

        assertEquals("1234", req.requestId)
        assertEquals("456", req.note?.id)
        assertEquals("title", req.note?.title)
        assertEquals("desc", req.note?.description)
        assertEquals("email", req.note?.email)
        assertEquals("2023-04-01T12:00:00Z", req.note?.timeReminder)
        assertEquals(ResponseResult.SUCCESS, req.result)
    }

    @Test
    fun fromTransportSearchRequest() {
        val req = NoteSearchRequest(
            requestId = "1234",
            debug = NoteDebug(
                mode = NoteRequestDebugMode.STUB,
                stub = NoteRequestDebugStubs.SUCCESS,
            ),
            noteFilter = NoteSearchFilter(
                title = "title",
                dateStart = "2023-04-01",
                dateEnd = "2023-05-01"
            ),
        )

        val context = TnContext()
        context.fromTransport(req)

        assertEquals(TnStubs.SUCCESS, context.stubCase)
        assertEquals(TnWorkMode.STUB, context.workMode)
        assertEquals("title", context.noteFilter.searchTitle)
        assertEquals(LocalDate.parse("2023-04-01"), context.noteFilter.dateStart)
        assertEquals(LocalDate.parse("2023-05-01"), context.noteFilter.dateEnd)
    }

    @Test
    fun toTransportSearchResponse() {
        val context = TnContext(
            requestId = TnRequestId("1234"),
            command = TnCommand.SEARCH,
            notesResponse = mutableListOf(
                Note(
                    id = NoteId("456"),
                    title = "title1",
                    description = "desc1",
                    email = "email1",
                    timeReminder = Instant.parse("2023-04-01T12:00:00Z")
                ),
                Note(
                    id = NoteId("789"),
                    title = "title2",
                    description = "desc2",
                    email = "email2",
                    timeReminder = Instant.parse("2023-04-02T12:00:00Z")
                ),
            ),
            state = TnState.RUNNING,
        )

        val req = context.toTransportNote() as NoteSearchResponse

        assertEquals("1234", req.requestId)

        assertEquals("456", req.notes?.first()?.id)
        assertEquals("title1", req.notes?.first()?.title)
        assertEquals("desc1", req.notes?.first()?.description)
        assertEquals("email1", req.notes?.first()?.email)
        assertEquals("2023-04-01T12:00:00Z", req.notes?.first()?.timeReminder)

        assertEquals("789", req.notes?.last()?.id)
        assertEquals("title2", req.notes?.last()?.title)
        assertEquals("desc2", req.notes?.last()?.description)
        assertEquals("email2", req.notes?.last()?.email)
        assertEquals("2023-04-02T12:00:00Z", req.notes?.last()?.timeReminder)

        assertEquals(ResponseResult.SUCCESS, req.result)
    }

}
