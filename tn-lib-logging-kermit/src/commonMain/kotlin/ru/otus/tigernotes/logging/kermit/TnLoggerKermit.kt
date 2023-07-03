package ru.otus.tigernotes.logging.kermit

import co.touchlab.kermit.Logger
import co.touchlab.kermit.Severity
import co.touchlab.kermit.StaticConfig
import ru.otus.tigernotes.logging.common.ITnLogWrapper
import kotlin.reflect.KClass

@Suppress("unused")
fun tnLoggerKermit(loggerId: String): ITnLogWrapper {
    val logger = Logger(
        config = StaticConfig(
            minSeverity = Severity.Info,
        ),
        tag = "DEV"
    )
    return TnLoggerWrapperKermit(
        logger = logger,
        loggerId = loggerId,
    )
}

@Suppress("unused")
fun tnLoggerKermit(cls: KClass<*>): ITnLogWrapper {
    val logger = Logger(
        config = StaticConfig(
            minSeverity = Severity.Info,
        ),
        tag = "DEV"
    )
    return TnLoggerWrapperKermit(
        logger = logger,
        loggerId = cls.qualifiedName?: "",
    )
}
