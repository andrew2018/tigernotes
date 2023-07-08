package ru.otus.tigernotes.app

import io.ktor.server.application.*
import ru.otus.tigernotes.api.models.*
import ru.otus.tigernotes.common.models.TnCommand
import ru.otus.tigernotes.logging.common.ITnLogWrapper

suspend fun ApplicationCall.createNote(appSettings: TnAppSettings, logger: ITnLogWrapper) {
    process<NoteCreateRequest, NoteCreateResponse>(appSettings, logger, "note-create", TnCommand.CREATE)
}

suspend fun ApplicationCall.readNote(appSettings: TnAppSettings, logger: ITnLogWrapper) {
    process<NoteReadRequest, NoteReadResponse>(appSettings, logger, "note-read", TnCommand.READ)
}

suspend fun ApplicationCall.updateNote(appSettings: TnAppSettings, logger: ITnLogWrapper) {
    process<NoteUpdateRequest, NoteUpdateResponse>(appSettings, logger, "note-update", TnCommand.UPDATE)
}

suspend fun ApplicationCall.deleteNote(appSettings: TnAppSettings, logger: ITnLogWrapper) {
    process<NoteDeleteRequest, NoteDeleteResponse>(appSettings, logger, "note-delete", TnCommand.DELETE)
}

suspend fun ApplicationCall.searchNote(appSettings: TnAppSettings, logger: ITnLogWrapper) {
    process<NoteSearchRequest, NoteSearchResponse>(appSettings, logger, "note-search", TnCommand.SEARCH)
}
