package ru.otus.tigernotes.acceptance.rest.action

import io.kotest.assertions.asClue
import io.kotest.assertions.withClue
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import ru.otus.tigernotes.acceptance.fixture.client.Client
import ru.otus.tigernotes.api.models.NoteCreateRequest
import ru.otus.tigernotes.api.models.NoteCreateResponse
import ru.otus.tigernotes.api.models.NoteObject
import ru.otus.tigernotes.api.models.NoteResponseObject

suspend fun Client.createNote(note: NoteObject = someCreateNote): NoteResponseObject = createNote(note) {
    it should haveSuccessResult
    it.note shouldNotBe null
    it.note?.apply {
        title shouldBe note.title
        description shouldBe note.description
        timeCreate shouldBe note.timeCreate
        email shouldBe note.email
        timeReminder shouldBe note.timeReminder

    }
    it.note!!
}

suspend fun <T> Client.createNote(note: NoteObject = someCreateNote, block: (NoteCreateResponse) -> T): T =
    withClue("createNote: $note") {
        val response = sendAndReceive(
            "/app/note/create", NoteCreateRequest(
                requestType = "create",
                debug = debug,
                noteCreate = note
            )
        ) as NoteCreateResponse

        response.asClue(block)
    }
