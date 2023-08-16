package ru.otus.tigernotes.common.models

import kotlin.jvm.JvmInline

@JvmInline
value class TnUserId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = TnUserId("")
    }
}
