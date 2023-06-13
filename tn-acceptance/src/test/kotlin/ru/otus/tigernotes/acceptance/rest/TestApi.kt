package ru.otus.tigernotes.acceptance.rest

import io.kotest.assertions.asClue
import io.kotest.assertions.withClue
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldExist
import io.kotest.matchers.collections.shouldExistInOrder
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import ru.otus.tigernotes.acceptance.fixture.client.Client
import ru.otus.tigernotes.acceptance.rest.action.*
import ru.otus.tigernotes.api.models.NoteSearchFilter
import ru.otus.tigernotes.api.models.NoteUpdateObject

fun FunSpec.testApiNote(client: Client, prefix: String = "") {
    context(prefix) {
        test("Create Note ok") {
            client.createNote()
        }

        test("Read Note ok") {
            val created = client.createNote()
            client.readNote(created.id).asClue {
                it shouldBe created
            }
        }

        test("Update Note ok") {
            val created = client.createNote()
            client.updateNote(created.id, NoteUpdateObject(title = "Заметка 1"))
            client.readNote(created.id) {
                it.note shouldBe created
            }
        }

        test("Delete Note ok") {
            val created = client.createNote()
            client.deleteNote(created.id)
//            client.readNote(created.id) {
//                // it should haveError("not-found")
//            }
        }

        test("Search Note ok") {
            val created1 = client.createNote(someCreateNote.copy(title = "title 123-01"))

            withClue("Search by Title") {
                val results = client.searchNote(search = NoteSearchFilter(title = "title 123-01", dateStart = null, dateEnd = null))
                 results shouldHaveSize 6
                 results shouldExist { it.title == "title 123-01" }
            }

            withClue("Search by Date") {
                val results = client.searchNote(search = NoteSearchFilter(title = null, dateStart = "2023-04-01", dateEnd = "2023-05-01"))
                results shouldHaveSize 6
                results shouldExist { it.title == "title 123-01" }
            }
        }
    }

}