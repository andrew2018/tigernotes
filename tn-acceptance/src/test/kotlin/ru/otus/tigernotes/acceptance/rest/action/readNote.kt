package ru.otus.tigernotes.acceptance.rest.action

import io.kotest.assertions.asClue
import io.kotest.assertions.withClue
import io.kotest.matchers.should
import io.kotest.matchers.shouldNotBe
import ru.otus.tigernotes.acceptance.fixture.client.Client
import ru.otus.tigernotes.api.models.NoteDebug
import ru.otus.tigernotes.api.models.NoteReadRequest
import ru.otus.tigernotes.api.models.NoteReadResponse
import ru.otus.tigernotes.api.models.NoteResponseObject

suspend fun Client.readNote(id: String?, mode: NoteDebug = debug): NoteResponseObject = readNote(id, mode) {
    it should haveSuccessResult
    it.note shouldNotBe null
    it.note!!
}

suspend fun <T> Client.readNote(id: String?, mode: NoteDebug = debug, block: (NoteReadResponse) -> T): T =
    withClue("readNote: $id") {
        id should beValidId

        val response = sendAndReceive(
            "/app/note/read",
            NoteReadRequest(
                requestType = "read",
                debug = mode,
                noteId = id
            )
        ) as NoteReadResponse

        response.asClue(block)
    }
