package ru.otus.tigernotes.acceptance.rest.action

import io.kotest.assertions.asClue
import io.kotest.assertions.withClue
import io.kotest.matchers.should
import ru.otus.tigernotes.acceptance.fixture.client.Client
import ru.otus.tigernotes.api.models.NoteResponseObject
import ru.otus.tigernotes.api.models.NoteSearchFilter
import ru.otus.tigernotes.api.models.NoteSearchRequest
import ru.otus.tigernotes.api.models.NoteSearchResponse

suspend fun Client.searchNote(search: NoteSearchFilter): List<NoteResponseObject> = searchNote(search) {
    it should haveSuccessResult
    it.notes ?: listOf()
}

suspend fun <T> Client.searchNote(search: NoteSearchFilter, block: (NoteSearchResponse) -> T): T =
    withClue("searchNote: $search") {
        val response = sendAndReceive(
            "/app/note/search",
            NoteSearchRequest(
                requestType = "search",
                debug = debug,
                noteFilter = search,
            )
        ) as NoteSearchResponse

        response.asClue(block)
    }
