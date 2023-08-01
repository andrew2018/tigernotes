package ru.otus.tigernotes.acceptance.rest

import io.kotest.assertions.asClue
import io.kotest.assertions.withClue
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldExist
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import ru.otus.tigernotes.acceptance.fixture.client.Client
import ru.otus.tigernotes.acceptance.rest.action.*
import ru.otus.tigernotes.api.models.NoteSearchFilter
import ru.otus.tigernotes.api.models.NoteUpdateObject
import java.time.LocalDate

fun FunSpec.testStubApiNote(client: Client, prefix: String = "") {
    context(prefix) {
        test("Create Note ok") {
            client.createNote(mode = prod)
        }

        test("Read Note ok") {
            val created = client.createNote(mode = prod)
            client.readNote(created.id, mode = prod).asClue {
                it.id shouldBe created.id
            }
        }

        test("Update Note ok") {
            val created = client.createNote(mode = prod)
            client.updateNote(created.id, created.lock, NoteUpdateObject(
                title = "Заметка New",
                description = "Описание New"), mode = prod)
            client.readNote(created.id, mode = prod) {
                it.note?.id shouldBe created.id
                it.note?.title shouldBe "Заметка New"
                it.note?.description shouldBe "Описание New"
            }
        }

        test("Delete Note ok") {
            val created = client.createNote(mode = prod)
            client.deleteNote(created.id, created.lock, mode = prod)
            client.readNote(created.id, mode = prod) {
                 it should haveError("not-found")
            }
        }

        test("Search Note ok") {
            client.createNote(someCreateNote.copy(title = "Заметка для поиска 1"), mode = prod)
            client.createNote(someCreateNote.copy(title = "Заметка для поиска 2"), mode = prod)

            withClue("Search by Title") {
                val results = client.searchNote(search = NoteSearchFilter(title = "Заметка для поиска 1", dateStart = null, dateEnd = null), mode = prod)
                 results shouldHaveSize 1
                 results shouldExist { it.title == "Заметка для поиска 1" }
            }

            withClue("Search by Date") {
                val results = client.searchNote(search = NoteSearchFilter(
                    title = "Заметка для поиска",
                    dateStart = LocalDate.now().minusDays(1).toString(),
                    dateEnd = LocalDate.now().plusDays(1).toString()), mode = prod)
                results shouldHaveSize 2
                results shouldExist { it.title == "Заметка для поиска 1" }
                results shouldExist { it.title == "Заметка для поиска 2" }
            }
        }
    }

}