package ru.otus.tigernotes.acceptance.rest.action

import io.kotest.assertions.asClue
import io.kotest.assertions.withClue
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import ru.otus.tigernotes.acceptance.fixture.client.Client
import ru.otus.tigernotes.api.models.*

suspend fun Client.updateNote(id: String?, lock: String?, note: NoteUpdateObject, mode: NoteDebug = debug): NoteResponseObject =
    updateNote(id, lock, note, mode) {
        it should haveSuccessResult
        it.note shouldNotBe null
        it.note?.apply {
            if (note.title != null)
                title shouldBe note.title
            if (note.description != null)
                description shouldBe note.description
            if (note.email != null)
                email shouldBe note.email
            if (note.timeReminder != null)
                timeReminder shouldBe note.timeReminder
        }
        it.note!!
    }

suspend fun <T> Client.updateNote(id: String?, lock: String?, note: NoteUpdateObject, mode: NoteDebug = debug, block: (NoteUpdateResponse) -> T): T =
    withClue("updatedNote: $id, lock: $lock, set: $note") {
        id should beValidId
        lock should beValidLock

        val response = sendAndReceive(
            "/app/note/update", NoteUpdateRequest(
                requestType = "update",
                debug = mode,
                noteUpdate = note.copy(id = id, lock = lock)
            )
        ) as NoteUpdateResponse

        response.asClue(block)
    }
