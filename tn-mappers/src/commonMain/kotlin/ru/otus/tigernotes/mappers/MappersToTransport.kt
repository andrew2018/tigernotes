package ru.otus.tigernotes.mappers

import kotlinx.datetime.Instant
import ru.otus.tigernotes.common.NONE
import ru.otus.tigernotes.common.TnContext
import ru.otus.tigernotes.common.models.*
import ru.otus.tigernotes.mappers.exceptions.UnknownTnCommand
import ru.otus.tigernotes.api.models.*

fun TnContext.toTransportNote(): IResponse = when (val cmd = command) {
    TnCommand.CREATE -> toTransportCreate()
    TnCommand.READ -> toTransportRead()
    TnCommand.UPDATE -> toTransportUpdate()
    TnCommand.DELETE -> toTransportDelete()
    TnCommand.SEARCH -> toTransportSearch()
    TnCommand.NONE -> throw UnknownTnCommand(cmd)
}

fun TnContext.toTransportCreate() = NoteCreateResponse(
    responseType = "create",
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == TnState.FINISHING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    note = noteResponse.toTransportNote()
)

fun TnContext.toTransportRead() = NoteReadResponse(
    responseType = "read",
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == TnState.FINISHING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    note = noteResponse.toTransportNote()
)

fun TnContext.toTransportUpdate() = NoteUpdateResponse(
    responseType = "update",
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == TnState.FINISHING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    note = noteResponse.toTransportNote()
)

fun TnContext.toTransportDelete() = NoteDeleteResponse(
    responseType = "delete",
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == TnState.FINISHING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    note = noteResponse.toTransportNote()
)

fun TnContext.toTransportSearch() = NoteSearchResponse(
    responseType = "search",
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == TnState.FINISHING) ResponseResult.SUCCESS else ResponseResult.ERROR,
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
    ownerId = ownerId.takeIf { it != TnUserId.NONE }?.asString(),
    timeCreate = timeCreate.takeIf { it != Instant.NONE }.toString(),
    email = email.takeIf { it.isNotBlank() },
    timeReminder = timeReminder.takeIf { it != Instant.NONE }.toString(),
    permissions = permissionsClient.toTransportNote(),
    lock = lock.takeIf { it != NoteLock.NONE }?.asString()
)

private fun Set<NotePermissionClient>.toTransportNote(): Set<NotePermissions>? = this
    .map { it.toTransportNote() }
    .toSet()
    .takeIf { it.isNotEmpty() }

private fun NotePermissionClient.toTransportNote() = when (this) {
    NotePermissionClient.READ -> NotePermissions.READ
    NotePermissionClient.UPDATE -> NotePermissions.UPDATE
    NotePermissionClient.DELETE -> NotePermissions.DELETE
}

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
