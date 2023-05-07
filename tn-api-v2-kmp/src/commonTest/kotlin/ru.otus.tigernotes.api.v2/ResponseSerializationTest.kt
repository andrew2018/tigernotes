package ru.otus.tigernotes.api.v2

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import ru.otus.tigernotes.api.v2.models.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class ResponseSerializationTest {
    private val noteResponseObject = NoteResponseObject(
        title = "note title",
        description = "note description",
        email = "test@mail.tu",
        timeReminder = "15.05.2023",
        id = "456"
    )

    private val createResponse: IResponse = NoteCreateResponse(
        responseType = "create",
        requestId = "123",
        note = noteResponseObject
    )

    private val readResponse: IResponse = NoteReadResponse(
        responseType = "read",
        requestId = "456",
        note = noteResponseObject
    )

    private val updateResponse: IResponse = NoteUpdateResponse(
        responseType = "update",
        requestId = "789",
        note = noteResponseObject
    )

    private val deleteResponse: IResponse = NoteDeleteResponse(
        responseType = "delete",
        requestId = "111",
        note = noteResponseObject
    )

    private val searchResponse: IResponse = NoteSearchResponse(
        responseType = "search",
        requestId = "222",
        notes = listOf(noteResponseObject, noteResponseObject.copy(id = "98"))
    )

    @Test
    fun serialize() {
        val createJson = apiV2Mapper.encodeToString(createResponse)
        assertContains(createJson, Regex("\"title\":\\s*\"note title\""))
        assertContains(createJson, Regex("\"description\":\\s*\"note description\""))
        assertContains(createJson, Regex("\"email\":\\s*\"test@mail.tu\""))
        assertContains(createJson, Regex("\"timeReminder\":\\s*\"15.05.2023\""))
        assertContains(createJson, Regex("\"requestId\":\\s*\"123\""))
        assertContains(createJson, Regex("\"responseType\":\\s*\"create\""))

        val readJson = apiV2Mapper.encodeToString(readResponse)
        assertContains(readJson, Regex("\"title\":\\s*\"note title\""))
        assertContains(readJson, Regex("\"description\":\\s*\"note description\""))
        assertContains(readJson, Regex("\"email\":\\s*\"test@mail.tu\""))
        assertContains(readJson, Regex("\"timeReminder\":\\s*\"15.05.2023\""))
        assertContains(readJson, Regex("\"requestId\":\\s*\"456\""))
        assertContains(readJson, Regex("\"responseType\":\\s*\"read\""))

        val updateJson = apiV2Mapper.encodeToString(updateResponse)
        assertContains(updateJson, Regex("\"title\":\\s*\"note title\""))
        assertContains(updateJson, Regex("\"description\":\\s*\"note description\""))
        assertContains(updateJson, Regex("\"email\":\\s*\"test@mail.tu\""))
        assertContains(updateJson, Regex("\"timeReminder\":\\s*\"15.05.2023\""))
        assertContains(updateJson, Regex("\"requestId\":\\s*\"789\""))
        assertContains(updateJson, Regex("\"responseType\":\\s*\"update\""))

        val deleteJson = apiV2Mapper.encodeToString(deleteResponse)
        assertContains(deleteJson, Regex("\"title\":\\s*\"note title\""))
        assertContains(deleteJson, Regex("\"description\":\\s*\"note description\""))
        assertContains(deleteJson, Regex("\"email\":\\s*\"test@mail.tu\""))
        assertContains(deleteJson, Regex("\"timeReminder\":\\s*\"15.05.2023\""))
        assertContains(deleteJson, Regex("\"requestId\":\\s*\"111\""))
        assertContains(deleteJson, Regex("\"responseType\":\\s*\"delete\""))

        val searchJson = apiV2Mapper.encodeToString(searchResponse)
        assertContains(searchJson, Regex("\"id\":\\s*\"456\""))
        assertContains(searchJson, Regex("\"id\":\\s*\"98\""))
        assertContains(searchJson, Regex("\"requestId\":\\s*\"222\""))
        assertContains(searchJson, Regex("\"responseType\":\\s*\"search\""))
    }

    @Test
    fun deserialize() {
        val json = apiV2Mapper.encodeToString(createResponse)
        val obj = apiV2Mapper.decodeFromString(json) as NoteCreateResponse

        assertEquals(createResponse, obj)
    }

    @Test
    fun deserializeNaked() {
        val jsonString = """
            {
            "responseType":"create",
            "requestId":"123",
            "result":null,
            "errors":null,
            "note":{"title":"note title","description":"note description","email":"test@mail.tu","timeReminder":"15.05.2023","id":null,"id":"456"}
            }
        """.trimIndent()
        val obj = apiV2Mapper.decodeFromString(jsonString) as IResponse

        assertEquals("123", obj.requestId)
        assertEquals(createResponse, obj)
    }
}
