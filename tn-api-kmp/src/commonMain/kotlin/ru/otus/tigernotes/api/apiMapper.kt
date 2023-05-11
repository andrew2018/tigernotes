package ru.otus.tigernotes.api

import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import ru.otus.tigernotes.api.models.*

/**
 * Добавляйте сюда элементы при появлении новых наследников IRequest / IResponse
 */
internal val infos = listOf(
    info(NoteCreateRequest::class, IRequest::class, "create") { copy(requestType = it) },
    info(NoteReadRequest::class, IRequest::class, "read") { copy(requestType = it) },
    info(NoteUpdateRequest::class, IRequest::class, "update") { copy(requestType = it) },
    info(NoteDeleteRequest::class, IRequest::class, "delete") { copy(requestType = it) },
    info(NoteSearchRequest::class, IRequest::class, "search") { copy(requestType = it) },

    info(NoteCreateResponse::class, IResponse::class, "create") { copy(responseType = it) },
    info(NoteReadResponse::class, IResponse::class, "read") { copy(responseType = it) },
    info(NoteUpdateResponse::class, IResponse::class, "update") { copy(responseType = it) },
    info(NoteDeleteResponse::class, IResponse::class, "delete") { copy(responseType = it) },
    info(NoteSearchResponse::class, IResponse::class, "search") { copy(responseType = it) },
)

val apiMapper = Json {
    classDiscriminator = "_"
    encodeDefaults = true
    ignoreUnknownKeys = true

    serializersModule = SerializersModule {
        setupPolymorphic()
    }
}

