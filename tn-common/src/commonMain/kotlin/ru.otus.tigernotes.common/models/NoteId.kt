package ru.otus.tigernotes.common.models

import kotlin.jvm.JvmInline

@JvmInline
value class NoteId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = NoteId("")
    }
}
