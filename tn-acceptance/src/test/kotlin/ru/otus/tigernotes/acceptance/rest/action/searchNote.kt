package ru.otus.tigernotes.acceptance.rest.action

import io.kotest.assertions.asClue
import io.kotest.assertions.withClue
import io.kotest.matchers.should
import ru.otus.tigernotes.acceptance.fixture.client.Client
import ru.otus.tigernotes.api.models.*

suspend fun Client.searchNote(search: NoteSearchFilter, mode: NoteDebug = debug): List<NoteResponseObject> = searchNote(search, mode) {
    it should haveSuccessResult
    it.notes ?: listOf()
}

suspend fun <T> Client.searchNote(search: NoteSearchFilter, mode: NoteDebug = debug, block: (NoteSearchResponse) -> T): T =
    withClue("searchNote: $search") {
        val response = sendAndReceive(
            "/app/note/search",
            NoteSearchRequest(
                requestType = "search",
                debug = mode,
                noteFilter = search,
            )
        ) as NoteSearchResponse

        response.asClue(block)
    }
