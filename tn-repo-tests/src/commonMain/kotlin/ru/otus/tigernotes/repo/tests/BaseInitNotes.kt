package ru.otus.tigernotes.repo.tests

import kotlinx.datetime.Instant
import ru.otus.tigernotes.common.models.Note
import ru.otus.tigernotes.common.models.NoteId
import ru.otus.tigernotes.common.models.NoteLock
import ru.otus.tigernotes.common.models.TnUserId

abstract class BaseInitNotes(val op: String): IInitObjects<Note> {

    open val lockOld: NoteLock = NoteLock("20000000-0000-0000-0000-000000000001")
    open val lockBad: NoteLock = NoteLock("20000000-0000-0000-0000-000000000009")

    fun createInitTestModel(
        suf: String,
        ownerId: TnUserId = TnUserId("owner-123"),
        timeCreate: Instant = Instant.parse("2021-04-01T11:00:00Z"),
        timeReminder: Instant = Instant.parse("2023-05-01T11:00:00Z"),
        lock: NoteLock = lockOld
    ) = Note(
        id = NoteId("note-repo-$op-$suf"),
        title = "$suf stub",
        description = "$suf stub description",
        ownerId = ownerId,
        timeCreate = timeCreate,
        email = "$suf stub email",
        timeReminder = timeReminder,
        lock = lock
    )
}
