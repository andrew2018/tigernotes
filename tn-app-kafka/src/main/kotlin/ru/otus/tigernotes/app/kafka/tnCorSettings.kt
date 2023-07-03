package ru.otus.tigernotes.app.kafka

import ru.otus.tigernotes.common.TnCorSettings
import ru.otus.tigernotes.logging.common.TnLoggerProvider
import ru.otus.tigernotes.logging.kermit.tnLoggerKermit

private val loggerProvider = TnLoggerProvider { tnLoggerKermit(it) }

val corSettings = TnCorSettings(
    loggerProvider = loggerProvider
)