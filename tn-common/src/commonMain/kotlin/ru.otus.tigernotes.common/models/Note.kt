package ru.otus.tigernotes.common.models

import kotlinx.datetime.Instant
import ru.otus.tigernotes.common.NONE

data class Note(
    var id: NoteId = NoteId.NONE,
    var title: String = "",
    var description: String = "",
    var timeCreate: Instant = Instant.NONE,
    var email: String = "",
    var timeReminder: Instant = Instant.NONE,
    var lock: NoteLock = NoteLock.NONE,
) {
    fun isEmpty() = this == NONE

    companion object {
        val NONE = Note()
    }
}
