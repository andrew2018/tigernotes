package ru.otus.tigernotes.app.repo

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import ru.otus.tigernotes.api.apiMapper
import ru.otus.tigernotes.api.models.*
import ru.otus.tigernotes.app.auth.addAuth
import ru.otus.tigernotes.app.config.KtorAuthConfig
import ru.otus.tigernotes.common.models.NoteId
import ru.otus.tigernotes.common.models.NoteLock
import ru.otus.tigernotes.stubs.NoteStub
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class NoteInmemoryApiTest {
    private val uuid = "10000000-0000-0000-0000-000000000001"

    private val initNote = NoteStub.prepareResult {
        id = NoteId(uuid)
        title = "abc"
        description = "abc"
        lock = NoteLock(uuid)
    }
    private val userId = initNote.ownerId

    private val createNote = NoteObject(
        title = "Заметка",
        description = "описание",
        timeCreate = "2023-04-01T10:00:00Z",
        email = "m@mail.ru",
        timeReminder = "2023-04-01T11:00:00Z"
    )
    private val requestObj = NoteCreateRequest(
        requestId = "12345",
        noteCreate = createNote,
        debug = NoteDebug(
            mode = NoteRequestDebugMode.TEST,
        )
    )

    @Test
    fun create() = testApplication {
        val responseObj = initObject(client)
        assertEquals(createNote.title, responseObj.note?.title)
        assertEquals(createNote.description, responseObj.note?.description)
        assertEquals(createNote.email, responseObj.note?.email)
        assertEquals(createNote.timeReminder, responseObj.note?.timeReminder)
    }

    @Test
    fun read() = testApplication {
        val noteCreateResponse = initObject(client)
        val oldId = noteCreateResponse.note?.id
        val response = client.post("/app/note/read") {

            val requestObj = NoteReadRequest(
                requestId = "12345",
                noteId = oldId,
                debug = NoteDebug(
                    mode = NoteRequestDebugMode.TEST,
                )
            )
            addAuth(id = userId.asString(), config = KtorAuthConfig.TEST)
            contentType(ContentType.Application.Json)
            val requestJson = apiMapper.encodeToString(requestObj)
            setBody(requestJson)
        }
        val responseJson = response.bodyAsText()
        val responseObj = apiMapper.decodeFromString<NoteReadResponse>(responseJson)
        assertEquals(200, response.status.value)
        assertEquals(oldId, responseObj.note?.id)
    }

    @Test
    fun update() = testApplication {
        val initObject = initObject(client)
        val noteUpdate = NoteUpdateObject(
            id = initObject.note?.id,
            title = "Обновление заголовка",
            description = "Обновление описания",
            email = "new@mail.ru",
            timeReminder = "2024-04-01T10:00:00Z",
            lock = initObject.note?.lock,
        )

        val response = client.post("/app/note/update") {
            val requestObj = NoteUpdateRequest(
                requestId = "12345",
                noteUpdate = noteUpdate,
                debug = NoteDebug(
                    mode = NoteRequestDebugMode.TEST,
                )
            )
            addAuth(id = userId.asString(), config = KtorAuthConfig.TEST)
            contentType(ContentType.Application.Json)
            val requestJson = apiMapper.encodeToString(requestObj)
            setBody(requestJson)
        }
        val responseJson = response.bodyAsText()
        val responseObj = apiMapper.decodeFromString<NoteUpdateResponse>(responseJson)
        assertEquals(200, response.status.value)
        assertEquals(noteUpdate.id, responseObj.note?.id)
        assertEquals(noteUpdate.title, responseObj.note?.title)
        assertEquals(noteUpdate.description, responseObj.note?.description)
    }

    @Test
    fun delete() = testApplication {
        val initObject = initObject(client)
        val id = initObject.note?.id
        val response = client.post("/app/note/delete") {
            val requestObj = NoteDeleteRequest(
                requestId = "12345",
                noteDelete = NoteDeleteObject(
                    id = id,
                    lock = initObject.note?.lock,
                ),
                debug = NoteDebug(
                    mode = NoteRequestDebugMode.TEST,
                )
            )
            addAuth(id = userId.asString(), config = KtorAuthConfig.TEST)
            contentType(ContentType.Application.Json)
            val requestJson = apiMapper.encodeToString(requestObj)
            setBody(requestJson)
        }
        val responseJson = response.bodyAsText()
        val responseObj = apiMapper.decodeFromString<NoteDeleteResponse>(responseJson)
        assertEquals(200, response.status.value)
        assertEquals(id, responseObj.note?.id)
    }

    @Test
    fun search() = testApplication {
        val initObject = initObject(client)
        val response = client.post("/app/note/search") {
            val requestObj = NoteSearchRequest(
                requestId = "12345",
                noteFilter = NoteSearchFilter(),
                debug = NoteDebug(
                    mode = NoteRequestDebugMode.TEST,
                )
            )
            addAuth(id = userId.asString(), config = KtorAuthConfig.TEST)
            contentType(ContentType.Application.Json)
            val requestJson = apiMapper.encodeToString(requestObj)
            setBody(requestJson)
        }
        val responseJson = response.bodyAsText()
        val responseObj = apiMapper.decodeFromString<NoteSearchResponse>(responseJson)
        assertEquals(200, response.status.value)
        assertNotEquals(0, responseObj.notes?.size)
        assertEquals(initObject.note?.id, responseObj.notes?.first()?.id)
    }

    private suspend fun initObject(client: HttpClient): NoteCreateResponse {
        val response = client.post("/app/note/create") {
            contentType(ContentType.Application.Json)
            addAuth(id = userId.asString(), config = KtorAuthConfig.TEST)
            val requestJson = apiMapper.encodeToString(requestObj)
            setBody(requestJson)
        }
        val responseJson = response.bodyAsText()
        assertEquals(200, response.status.value)
        return apiMapper.decodeFromString<NoteCreateResponse>(responseJson)
    }
}
