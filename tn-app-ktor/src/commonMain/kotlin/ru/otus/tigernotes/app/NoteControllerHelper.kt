package ru.otus.tigernotes.app

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import ru.otus.tigernotes.api.apiMapper
import ru.otus.tigernotes.api.models.*
import ru.otus.tigernotes.biz.process
import ru.otus.tigernotes.common.models.TnCommand
import ru.otus.tigernotes.logging.common.ITnLogWrapper
import ru.otus.tigernotes.mappers.*
import ru.otus.tigernotes.mappers.log.toLog

suspend inline fun <reified Q : IRequest, @Suppress("unused") reified R : IResponse> ApplicationCall.process(
    appSettings: TnAppSettings,
    logger: ITnLogWrapper,
    logId: String,
    command: TnCommand,
) {
    appSettings.processor.process(logger, logId, command,
        {ctx ->
            val request = apiMapper.decodeFromString<Q>(receiveText())
            ctx.fromTransport(request)
        },
        { ctx ->
            respond(apiMapper.encodeToString(ctx.toTransportNote()))
        },
        { logId -> toLog(logId) }
    )
}
