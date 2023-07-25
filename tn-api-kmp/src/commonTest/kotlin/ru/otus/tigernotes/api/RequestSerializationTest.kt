package ru.otus.tigernotes.api

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import ru.otus.tigernotes.api.models.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class RequestSerializationTest {
    private val createRequest : IRequest = NoteCreateRequest(
        requestType= "create",
        requestId = "123",
        debug = NoteDebug(
            mode = NoteRequestDebugMode.STUB,
            stub = NoteRequestDebugStubs.BAD_TITLE
        ),
        noteCreate = NoteObject(
            title = "note title",
            description = "note description",
            email = "test@mail.tu",
            timeReminder = "15.05.2023"
        )
    )

    private val readRequest : IRequest = NoteReadRequest(
        requestType= "read",
        requestId = "789",
        debug = NoteDebug(
            mode = NoteRequestDebugMode.STUB,
            stub = NoteRequestDebugStubs.BAD_TITLE
        ),
        noteId = "123"
    )

    private val updateRequest : IRequest = NoteUpdateRequest(
        requestType= "update",
        requestId = "789",
        debug = NoteDebug(
            mode = NoteRequestDebugMode.STUB,
            stub = NoteRequestDebugStubs.BAD_TITLE
        ),
        noteUpdate = NoteUpdateObject(
            title = "note title",
            description = "note description",
            email = "test@mail.tu",
            timeReminder = "15.05.2023",
            id = "456"
        )
    )

    private val deleteRequest : IRequest = NoteDeleteRequest(
        requestType= "delete",
        requestId = "789",
        debug = NoteDebug(
            mode = NoteRequestDebugMode.STUB,
            stub = NoteRequestDebugStubs.BAD_TITLE
        ),
        noteDelete = NoteDeleteObject(id = "123")
    )

    private val searchRequest : IRequest = NoteSearchRequest(
        requestType= "search",
        requestId = "789",
        debug = NoteDebug(
            mode = NoteRequestDebugMode.STUB,
            stub = NoteRequestDebugStubs.BAD_TITLE
        ),
        noteFilter = NoteSearchFilter(
            title = "note title",
            dateStart = "03.04.2023",
            dateEnd = "10.04.2023"
        )
    )

    @Test
    fun serialize() {
        val createJson = apiMapper.encodeToString(createRequest)
        assertContains(createJson, Regex("\"title\":\\s*\"note title\""))
        assertContains(createJson, Regex("\"description\":\\s*\"note description\""))
        assertContains(createJson, Regex("\"email\":\\s*\"test@mail.tu\""))
        assertContains(createJson, Regex("\"mode\":\\s*\"stub\""))
        assertContains(createJson, Regex("\"stub\":\\s*\"badTitle\""))
        assertContains(createJson, Regex("\"requestType\":\\s*\"create\""))

        val readJson = apiMapper.encodeToString(readRequest)
        assertContains(readJson, Regex("\"noteId\":\\s*\"123\""))
        assertContains(readJson, Regex("\"mode\":\\s*\"stub\""))
        assertContains(readJson, Regex("\"stub\":\\s*\"badTitle\""))
        assertContains(readJson, Regex("\"requestType\":\\s*\"read\""))

        val updateJson = apiMapper.encodeToString(updateRequest)
        assertContains(updateJson, Regex("\"title\":\\s*\"note title\""))
        assertContains(updateJson, Regex("\"requestId\":\\s*\"789\""))
        assertContains(updateJson, Regex("\"stub\":\\s*\"badTitle\""))
        assertContains(updateJson, Regex("\"id\":\\s*\"456\""))

        val deleteJson = apiMapper.encodeToString(deleteRequest)
        assertContains(deleteJson, Regex("\"id\":\\s*\"123\""))
        assertContains(deleteJson, Regex("\"mode\":\\s*\"stub\""))
        assertContains(deleteJson, Regex("\"stub\":\\s*\"badTitle\""))
        assertContains(deleteJson, Regex("\"requestType\":\\s*\"delete\""))

        val searchJson = apiMapper.encodeToString(searchRequest)
        assertContains(searchJson, Regex("\"title\":\\s*\"note title\""))
        assertContains(searchJson, Regex("\"dateStart\":\\s*\"03.04.2023\""))
        assertContains(searchJson, Regex("\"dateEnd\":\\s*\"10.04.2023\""))
        assertContains(searchJson, Regex("\"mode\":\\s*\"stub\""))
        assertContains(searchJson, Regex("\"stub\":\\s*\"badTitle\""))
        assertContains(searchJson, Regex("\"requestType\":\\s*\"search\""))
    }

    @Test
    fun serializeWithoutType() {
        val json = apiMapper.encodeToString((createRequest as NoteCreateRequest).copy(requestType = null) as IRequest)

        assertContains(json, Regex("\"title\":\\s*\"note title\""))
        assertContains(json, Regex("\"mode\":\\s*\"stub\""))
        assertContains(json, Regex("\"stub\":\\s*\"badTitle\""))
    }

    @Test
    fun deserialize() {
        val json = apiMapper.encodeToString(createRequest)
        val obj = apiMapper.decodeFromString(json) as NoteCreateRequest

        assertEquals(createRequest, obj)
    }
    @Test
    fun deserializeNaked() {
        val jsonString = """
            {
            "requestType":"create",
            "requestId":"123",
            "debug":{"mode":"stub","stub":"badTitle"},
            "noteCreate":{"title":"note title","description":"note description","email":"test@mail.tu","timeReminder":"15.05.2023"}
            }
        """.trimIndent()
        val obj = apiMapper.decodeFromString(jsonString) as IRequest

        assertEquals("123", obj.requestId)
        assertEquals(createRequest, obj)
    }
}
