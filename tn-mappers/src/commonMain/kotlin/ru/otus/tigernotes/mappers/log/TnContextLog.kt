package ru.otus.tigernotes.mappers.log

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import ru.otus.tigernotes.common.NONE
import ru.otus.tigernotes.common.TnContext
import ru.otus.tigernotes.common.models.*
import ru.otus.tigernotes.log.models.*

fun TnContext.toLog(logId: String) = CommonLogModel(
    messageTime = Clock.System.now().toString(),
    logId = logId,
    source = "ok-tigernotes",
    note = toTnLog(),
    errors = errors.map { it.toLog() },
)

fun TnContext.toTnLog(): TnLogModel? {
    val adNone = Note()
    return TnLogModel(
        requestId = requestId.takeIf { it != TnRequestId.NONE }?.asString(),
        requestNote = note.takeIf { it != adNone }?.toLog(),
        responseNote = noteResponse.takeIf { it != adNone }?.toLog(),
        responseNotes = notesResponse.takeIf { it.isNotEmpty() }?.filter { it != adNone }?.map { it.toLog() },
        requestFilter = noteFilter.takeIf { it != NoteFilter() }?.toLog(),
    ).takeIf { it != TnLogModel() }
}

private fun NoteFilter.toLog() = NoteFilterLog(
    searchTitle = searchTitle.takeIf { it.isNotBlank() },
    dateStart = dateStart.takeIf { it != LocalDate.NONE }.toString(),
    dateEnd = dateEnd.takeIf { it != LocalDate.NONE }.toString(),
)

fun TnError.toLog() = ErrorLogModel(
    message = message.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    code = code.takeIf { it.isNotBlank() },
    level = level.name,
)

fun Note.toLog() = NoteLog(
    id = id.takeIf { it != NoteId.NONE }?.asString(),
    title = title.takeIf { it.isNotBlank() },
    description = description.takeIf { it.isNotBlank() },
    timeCreate = timeCreate.takeIf { it != Instant.NONE }.toString(),
    email = email.takeIf { it.isNotBlank() },
    timeReminder = timeReminder.takeIf { it != Instant.NONE }.toString()
)
