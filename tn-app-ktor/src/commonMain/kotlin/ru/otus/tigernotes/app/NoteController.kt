package ru.otus.tigernotes.app

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import ru.otus.tigernotes.api.apiMapper
import ru.otus.tigernotes.api.models.*
import ru.otus.tigernotes.common.TnContext
import ru.otus.tigernotes.mappers.*

suspend fun ApplicationCall.createNote(processor: NoteProcessor) {
    val request = apiMapper.decodeFromString<NoteCreateRequest>(receiveText())
    val context = TnContext()
    context.fromTransport(request)
    processor.exec(context)
    respond(apiMapper.encodeToString(context.toTransportCreate()))
}

suspend fun ApplicationCall.readNote(processor: NoteProcessor) {
    val request = apiMapper.decodeFromString<NoteReadRequest>(receiveText())
    val context = TnContext()
    context.fromTransport(request)
    processor.exec(context)
    respond(apiMapper.encodeToString(context.toTransportRead()))
}

suspend fun ApplicationCall.updateNote(processor: NoteProcessor) {
    val request = apiMapper.decodeFromString<NoteUpdateRequest>(receiveText())
    val context = TnContext()
    context.fromTransport(request)
    processor.exec(context)
    respond(apiMapper.encodeToString(context.toTransportUpdate()))
}

suspend fun ApplicationCall.deleteNote(processor: NoteProcessor) {
    val request = apiMapper.decodeFromString<NoteDeleteRequest>(receiveText())
    val context = TnContext()
    context.fromTransport(request)
    processor.exec(context)
    respond(apiMapper.encodeToString(context.toTransportDelete()))
}

suspend fun ApplicationCall.searchNote(processor: NoteProcessor) {
    val request = apiMapper.decodeFromString<NoteSearchRequest>(receiveText())
    val context = TnContext()
    context.fromTransport(request)
    processor.exec(context)
    respond(apiMapper.encodeToString(context.toTransportSearch()))
}
