package ru.otus.tigernotes.common

import kotlinx.datetime.Instant
import ru.otus.tigernotes.common.stubs.TnStubs
import ru.otus.tigernotes.common.models.*

data class TnContext(
    var command: TnCommand = TnCommand.NONE,
    var state: TnState = TnState.NONE,
    val errors: MutableList<TnError> = mutableListOf(),
    var settings: TnCorSettings = TnCorSettings.NONE,

    var workMode: TnWorkMode = TnWorkMode.PROD,
    var stubCase: TnStubs = TnStubs.NONE,

    var requestId: TnRequestId = TnRequestId.NONE,
    var timeStart: Instant = Instant.NONE,
    var note: Note = Note(),
    var noteFilter: NoteFilter = NoteFilter(),

    var noteValidating: Note = Note(),
    var noteFilterValidating: NoteFilter = NoteFilter(),

    var noteValidated: Note = Note(),
    var noteFilterValidated: NoteFilter = NoteFilter(),

    var noteResponse: Note = Note(),
    var notesResponse: MutableList<Note> = mutableListOf(),
)
