package ru.otus.tigernotes.stubs

import kotlinx.datetime.Instant
import ru.otus.tigernotes.common.models.Note
import ru.otus.tigernotes.common.models.NoteId
import ru.otus.tigernotes.common.models.NoteLock

object NoteObjectStub {
    val NOTE_DEFAULT: Note
        get() = Note(
            id = NoteId("123"),
            title = "Заметка 1",
            description = "Сделать все задачи до завтра",
            timeCreate = Instant.parse("2023-04-01T10:00:00Z"),
            email = "test@mail.ru",
            timeReminder = Instant.parse("2023-04-01T11:00:00Z"),
            lock = NoteLock("123-234-abc-ABC")
        )
}
