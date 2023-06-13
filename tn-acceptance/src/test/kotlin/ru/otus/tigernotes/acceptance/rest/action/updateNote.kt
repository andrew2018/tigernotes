package ru.otus.tigernotes.acceptance.rest.action

import io.kotest.assertions.asClue
import io.kotest.assertions.withClue
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import ru.otus.tigernotes.acceptance.fixture.client.Client
import ru.otus.tigernotes.api.models.NoteResponseObject
import ru.otus.tigernotes.api.models.NoteUpdateObject
import ru.otus.tigernotes.api.models.NoteUpdateRequest
import ru.otus.tigernotes.api.models.NoteUpdateResponse

suspend fun Client.updateNote(id: String?, ad: NoteUpdateObject): NoteResponseObject =
    updateNote(id, ad) {
        it should haveSuccessResult
        it.note shouldBe noteStub
        it.note!!
    }

suspend fun <T> Client.updateNote(id: String?, note: NoteUpdateObject, block: (NoteUpdateResponse) -> T): T =
    withClue("updatedNote: $id, set: $note") {
        id should beValidId

        val response = sendAndReceive(
            "/app/note/update", NoteUpdateRequest(
                requestType = "update",
                debug = debug,
                noteUpdate = note.copy(id = id)
            )
        ) as NoteUpdateResponse

        response.asClue(block)
    }
