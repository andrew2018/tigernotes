package ru.otus.tigernotes.common.models

import kotlinx.datetime.Instant
import ru.otus.tigernotes.common.NONE

data class Note(
    var id: NoteId = NoteId.NONE,
    var title: String = "",
    var description: String = "",
    var email: String = "",
    var timeReminder: Instant = Instant.NONE
)
