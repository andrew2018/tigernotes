package ru.otus.tigernotes.app.stubs

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import ru.otus.tigernotes.api.apiMapper
import ru.otus.tigernotes.api.models.*
import kotlin.test.Test
import kotlin.test.assertEquals

class NoteStubApiTest {

    @Test
    fun create() = testApplication {
        val response = client.post("/app/note/create") {
            val requestObj = NoteCreateRequest(
                requestId = "12345",
                noteCreate = NoteObject(
                    title = "Заметка 1",
                    description = "заполнить все поля",
                    email = "email",
                    timeReminder = "2023-04-01T12:00:00Z"
                ),
                debug = NoteDebug(
                    mode = NoteRequestDebugMode.STUB,
                    stub = NoteRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            val requestJson = apiMapper.encodeToString(requestObj)
            setBody(requestJson)
        }
        val responseJson = response.bodyAsText()
        val responseObj = apiMapper.decodeFromString<NoteCreateResponse>(responseJson)
        assertEquals(200, response.status.value)
        assertEquals("123", responseObj.note?.id)
    }

    @Test
    fun read() = testApplication {
        val response = client.post("/app/note/read") {
            val requestObj = NoteReadRequest(
                requestId = "12345",
                noteId = "123",
                debug = NoteDebug(
                    mode = NoteRequestDebugMode.STUB,
                    stub = NoteRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            val requestJson = apiMapper.encodeToString(requestObj)
            setBody(requestJson)
        }
        val responseJson = response.bodyAsText()
        val responseObj = apiMapper.decodeFromString<NoteReadResponse>(responseJson)
        assertEquals(200, response.status.value)
        assertEquals("123", responseObj.note?.id)
    }

    @Test
    fun update() = testApplication {
        val response = client.post("/app/note/update") {
            val requestObj = NoteUpdateRequest(
                requestId = "12345",
                noteUpdate = NoteUpdateObject(
                    id = "123",
                    title = "Заметка 1",
                    description = "заполнить все поля",
                    email = "email",
                    timeReminder = "2023-04-01T12:00:00Z"
                ),
                debug = NoteDebug(
                    mode = NoteRequestDebugMode.STUB,
                    stub = NoteRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            val requestJson = apiMapper.encodeToString(requestObj)
            setBody(requestJson)
        }
        val responseJson = response.bodyAsText()
        val responseObj = apiMapper.decodeFromString<NoteUpdateResponse>(responseJson)
        assertEquals(200, response.status.value)
        assertEquals("123", responseObj.note?.id)
    }

    @Test
    fun delete() = testApplication {
        val response = client.post("/app/note/delete") {
            val requestObj = NoteDeleteRequest(
                requestId = "12345",
                noteId = "123",
                debug = NoteDebug(
                    mode = NoteRequestDebugMode.STUB,
                    stub = NoteRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            val requestJson = apiMapper.encodeToString(requestObj)
            setBody(requestJson)
        }
        val responseJson = response.bodyAsText()
        val responseObj = apiMapper.decodeFromString<NoteDeleteResponse>(responseJson)
        assertEquals(200, response.status.value)
        assertEquals("123", responseObj.note?.id)
    }

    @Test
    fun search() = testApplication {
        val response = client.post("/app/note/search") {
            val requestObj = NoteSearchRequest(
                requestId = "12345",
                noteFilter = NoteSearchFilter(),
                debug = NoteDebug(
                    mode = NoteRequestDebugMode.STUB,
                    stub = NoteRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            val requestJson = apiMapper.encodeToString(requestObj)
            setBody(requestJson)
        }
        val responseJson = response.bodyAsText()
        val responseObj = apiMapper.decodeFromString<NoteSearchResponse>(responseJson)
        assertEquals(200, response.status.value)
        assertEquals("123-01", responseObj.notes?.first()?.id)
    }
}
