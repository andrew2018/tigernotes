package ru.otus.tigernotes.logging.common

import kotlin.reflect.KClass
import kotlin.reflect.KFunction

class TnLoggerProvider(
    private val provider: (String) -> ITnLogWrapper = { ITnLogWrapper.DEFAULT }
) {
    fun logger(loggerId: String) = provider(loggerId)
    fun logger(clazz: KClass<*>) = provider(clazz.qualifiedName ?: clazz.simpleName ?: "(unknown)")
    fun logger(function: KFunction<*>) = provider(function.name)
}
