package ru.otus.tigernotes.mappers

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDate
import ru.otus.tigernotes.common.NONE
import ru.otus.tigernotes.common.TnContext
import ru.otus.tigernotes.common.models.*
import ru.otus.tigernotes.common.stubs.TnStubs
import ru.otus.tigernotes.mappers.exceptions.UnknownRequestClass
import ru.otus.tigernotes.api.models.*

fun TnContext.fromTransport(request: IRequest) = when (request) {
    is NoteCreateRequest -> fromTransport(request)
    is NoteReadRequest -> fromTransport(request)
    is NoteUpdateRequest -> fromTransport(request)
    is NoteDeleteRequest -> fromTransport(request)
    is NoteSearchRequest -> fromTransport(request)
    else -> throw UnknownRequestClass(request::class)
}

private fun String?.toNoteId() = this?.let { NoteId(it) } ?: NoteId.NONE
private fun String?.toNoteLock() = this?.let { NoteLock(it) } ?: NoteLock.NONE
private fun String?.toNoteWithId() = Note(id = this.toNoteId())
private fun IRequest?.requestId() = this?.requestId?.let { TnRequestId(it) } ?: TnRequestId.NONE

private fun NoteDebug?.transportToWorkMode(): TnWorkMode = when (this?.mode) {
    NoteRequestDebugMode.PROD -> TnWorkMode.PROD
    NoteRequestDebugMode.TEST -> TnWorkMode.TEST
    NoteRequestDebugMode.STUB -> TnWorkMode.STUB
    null -> TnWorkMode.PROD
}

private fun NoteDebug?.transportToStubCase(): TnStubs = when (this?.stub) {
    NoteRequestDebugStubs.SUCCESS -> TnStubs.SUCCESS
    NoteRequestDebugStubs.NOT_FOUND -> TnStubs.NOT_FOUND
    NoteRequestDebugStubs.BAD_ID -> TnStubs.BAD_ID
    NoteRequestDebugStubs.BAD_TITLE -> TnStubs.BAD_TITLE
    NoteRequestDebugStubs.BAD_DESCRIPTION -> TnStubs.BAD_DESCRIPTION
    NoteRequestDebugStubs.BAD_EMAIL -> TnStubs.BAD_EMAIL
    NoteRequestDebugStubs.BAD_TIME_REMINDER -> TnStubs.BAD_TIME_REMINDER
    NoteRequestDebugStubs.CANNOT_DELETE -> TnStubs.CANNOT_DELETE
    NoteRequestDebugStubs.BAD_SEARCH_TITLE -> TnStubs.BAD_SEARCH_TITLE
    NoteRequestDebugStubs.BAD_SEARCH_DATE_START -> TnStubs.BAD_SEARCH_DATE_START
    NoteRequestDebugStubs.BAD_SEARCH_DATE_END -> TnStubs.BAD_SEARCH_DATE_END
    null -> TnStubs.NONE
}

fun TnContext.fromTransport(request: NoteCreateRequest) {
    command = TnCommand.CREATE
    requestId = request.requestId()
    note = request.noteCreate?.toInternal() ?: Note()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun TnContext.fromTransport(request: NoteReadRequest) {
    command = TnCommand.READ
    requestId = request.requestId()
    note = request.noteId.toNoteWithId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun TnContext.fromTransport(request: NoteUpdateRequest) {
    command = TnCommand.UPDATE
    requestId = request.requestId()
    note = request.noteUpdate?.toInternal() ?: Note()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun TnContext.fromTransport(request: NoteDeleteRequest) {
    command = TnCommand.DELETE
    requestId = request.requestId()
    note = request.noteDelete.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun TnContext.fromTransport(request: NoteSearchRequest) {
    command = TnCommand.SEARCH
    requestId = request.requestId()
    noteFilter = request.noteFilter.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

private fun NoteSearchFilter?.toInternal(): NoteFilter = NoteFilter(
    searchTitle = this?.title ?: "",
    dateStart = this?.dateStart?.toLocalDate() ?: LocalDate.NONE,
    dateEnd = this?.dateEnd?.toLocalDate() ?: LocalDate.NONE
)

private fun NoteObject.toInternal(): Note = Note(
    title = this.title ?: "",
    description = this.description ?: "",
    timeCreate = this.timeCreate?.toInstant() ?: Instant.NONE,
    email = this.email ?: "",
    timeReminder = this.timeReminder?.toInstant() ?: Instant.NONE
)

private fun NoteUpdateObject.toInternal(): Note = Note(
    id = this.id.toNoteId(),
    title = this.title ?: "",
    description = this.description ?: "",
    timeCreate = this.timeCreate?.toInstant() ?: Instant.NONE,
    email = this.email ?: "",
    timeReminder = this.timeReminder?.toInstant() ?: Instant.NONE,
    lock = this.lock.toNoteLock(),
)

private fun NoteDeleteObject?.toInternal(): Note = if (this != null) {
    Note(
        id = id.toNoteId(),
        lock = lock.toNoteLock(),
    )
} else {
    Note.NONE
}
