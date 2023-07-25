package ru.otus.tigernotes.common.repo

import ru.otus.tigernotes.common.models.Note

data class DbNoteRequest(
    val note: Note
)
