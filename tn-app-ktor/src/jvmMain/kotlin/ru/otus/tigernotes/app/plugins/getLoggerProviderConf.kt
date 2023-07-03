package ru.otus.tigernotes.app.plugins

import io.ktor.server.application.*
import ru.otus.tigernotes.logging.common.TnLoggerProvider
import ru.otus.tigernotes.logging.kermit.tnLoggerKermit

actual fun Application.getLoggerProviderConf(): TnLoggerProvider = TnLoggerProvider {
    tnLoggerKermit(it)
}
