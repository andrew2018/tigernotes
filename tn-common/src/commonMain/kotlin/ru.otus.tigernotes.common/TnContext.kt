package ru.otus.tigernotes.common

import kotlinx.datetime.Instant
import ru.otus.tigernotes.common.stubs.TnStubs
import ru.otus.tigernotes.common.models.*
import ru.otus.tigernotes.common.permissions.TnPrincipalModel
import ru.otus.tigernotes.common.permissions.TnUserPermissions
import ru.otus.tigernotes.common.repo.INoteRepository

data class TnContext(
    var command: TnCommand = TnCommand.NONE,
    var state: TnState = TnState.NONE,
    val errors: MutableList<TnError> = mutableListOf(),
    var settings: TnCorSettings = TnCorSettings.NONE,

    var workMode: TnWorkMode = TnWorkMode.PROD,
    var stubCase: TnStubs = TnStubs.NONE,

    var principal: TnPrincipalModel = TnPrincipalModel.NONE,
    val permissionsChain: MutableSet<TnUserPermissions> = mutableSetOf(),
    var permitted: Boolean = false,

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

    var noteRepo: INoteRepository = INoteRepository.NONE,
    var noteRepoPrepare: Note = Note(),
    var noteRepoRead: Note = Note(),
    var noteRepoDone: Note = Note(),
    var notesRepoDone: MutableList<Note> = mutableListOf(),
)
