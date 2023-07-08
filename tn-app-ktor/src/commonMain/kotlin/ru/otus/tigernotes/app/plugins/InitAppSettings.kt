package ru.otus.tigernotes.app.plugins

import io.ktor.server.application.*
import ru.otus.tigernotes.biz.NoteProcessor
import ru.otus.tigernotes.app.TnAppSettings
import ru.otus.tigernotes.common.TnCorSettings
import ru.otus.tigernotes.logging.common.TnLoggerProvider

fun Application.initAppSettings(): TnAppSettings {
    val corSettings = TnCorSettings(
        loggerProvider = getLoggerProviderConf(),
    )
    return TnAppSettings(
        appUrls = environment.config.propertyOrNull("ktor.urls")?.getList() ?: emptyList(),
        corSettings = corSettings,
        processor = NoteProcessor(corSettings),
    )
}

expect fun Application.getLoggerProviderConf(): TnLoggerProvider
