package ru.otus.tigernotes.mappers

import kotlinx.datetime.Instant
import ru.otus.tigernotes.common.NONE
import ru.otus.tigernotes.common.TnContext
import ru.otus.tigernotes.common.models.*
import ru.otus.tigernotes.mappers.exceptions.UnknownTnCommand
import ru.otus.tigernotes.api.v2.models.*

fun TnContext.toTransportNote(): IResponse = when (val cmd = command) {
    TnCommand.CREATE -> toTransportCreate()
    TnCommand.READ -> toTransportRead()
    TnCommand.UPDATE -> toTransportUpdate()
    TnCommand.DELETE -> toTransportDelete()
    TnCommand.SEARCH -> toTransportSearch()
    TnCommand.NONE -> throw UnknownTnCommand(cmd)
}

fun TnContext.toTransportCreate() = NoteCreateResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == TnState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    note = noteResponse.toTransportNote()
)

fun TnContext.toTransportRead() = NoteReadResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == TnState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    note = noteResponse.toTransportNote()
)

fun TnContext.toTransportUpdate() = NoteUpdateResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == TnState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    note = noteResponse.toTransportNote()
)

fun TnContext.toTransportDelete() = NoteDeleteResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == TnState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    note = noteResponse.toTransportNote()
)

fun TnContext.toTransportSearch() = NoteSearchResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == TnState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    notes = notesResponse.toTransportAd()
)

fun List<Note>.toTransportAd(): List<NoteResponseObject>? = this
    .map { it.toTransportNote() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun Note.toTransportNote(): NoteResponseObject = NoteResponseObject(
    id = id.takeIf { it != NoteId.NONE }?.asString(),
    title = title.takeIf { it.isNotBlank() },
    description = description.takeIf { it.isNotBlank() },
    email = email.takeIf { it.isNotBlank() },
    timeReminder = timeReminder.takeIf { it != Instant.NONE }.toString(),
)

private fun List<TnError>.toTransportErrors(): List<Error>? = this
    .map { it.toTransportNote() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun TnError.toTransportNote() = Error(
    code = code.takeIf { it.isNotBlank() },
    group = group.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    message = message.takeIf { it.isNotBlank() },
)
