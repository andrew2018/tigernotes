package ru.otus.tigernotes.common

import ru.otus.tigernotes.logging.common.TnLoggerProvider

data class TnCorSettings(
    val loggerProvider: TnLoggerProvider = TnLoggerProvider(),
) {
    companion object {
        val NONE = TnCorSettings()
    }
}
