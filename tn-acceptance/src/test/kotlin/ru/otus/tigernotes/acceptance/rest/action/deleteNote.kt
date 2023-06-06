package ru.otus.tigernotes.acceptance.rest.action

import io.kotest.assertions.asClue
import io.kotest.assertions.withClue
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import ru.otus.tigernotes.acceptance.fixture.client.Client
import ru.otus.tigernotes.api.models.NoteDeleteRequest
import ru.otus.tigernotes.api.models.NoteDeleteResponse

suspend fun Client.deleteNote(id: String?) {
    withClue("deleteNote: $id") {
        id should beValidId

        val response = sendAndReceive(
            "/app/note/delete",
            NoteDeleteRequest(
                requestType = "delete",
                debug = debug,
                noteId = id
            )
        ) as NoteDeleteResponse

        response.asClue {
            response should haveSuccessResult
            response.note shouldNotBe null
            response.note?.id shouldBe id
        }
    }
}