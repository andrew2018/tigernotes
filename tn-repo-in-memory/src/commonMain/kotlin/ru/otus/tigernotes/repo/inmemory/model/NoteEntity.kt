package ru.otus.tigernotes.repo.inmemory.model

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import ru.otus.tigernotes.common.NONE
import ru.otus.tigernotes.common.models.Note
import ru.otus.tigernotes.common.models.NoteId
import ru.otus.tigernotes.common.models.NoteLock

data class NoteEntity(
    val id: String? = null,
    val title: String? = null,
    val description: String? = null,
    var timeCreate: Instant? = null,
    var email: String? = null,
    var timeReminder: Instant? = null,
    val lock: String? = null
) {
    constructor(model: Note): this(
        id = model.id.asString().takeIf { it.isNotBlank() },
        title = model.title.takeIf { it.isNotBlank() },
        description = model.description.takeIf { it.isNotBlank() },
        timeCreate = Clock.System.now(),
        email = model.email.takeIf { it.isNotBlank() },
        timeReminder = model.timeReminder.takeIf { it != Instant.NONE },
        lock = model.lock.asString().takeIf { it.isNotBlank() }
    )

    fun toInternal() = Note(
        id = id?.let { NoteId(it) }?: NoteId.NONE,
        title = title?: "",
        description = description?: "",
        timeCreate = timeCreate?: Instant.NONE,
        email = email?: "",
        timeReminder = timeReminder?: Instant.NONE,
        lock = lock?.let { NoteLock(it) } ?: NoteLock.NONE
    )
}
