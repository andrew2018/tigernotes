package ru.otus.tigernotes.common.repo

import ru.otus.tigernotes.common.models.Note
import ru.otus.tigernotes.common.models.NoteId
import ru.otus.tigernotes.common.models.NoteLock

data class DbNoteIdRequest(
    val id: NoteId,
    val lock: NoteLock = NoteLock.NONE,
) {
    constructor(note: Note): this(note.id, note.lock)
}
