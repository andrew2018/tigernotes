package ru.otus.tigernotes.acceptance.rest.action

import ru.otus.tigernotes.api.models.*

val debug = NoteDebug(mode = NoteRequestDebugMode.STUB, stub = NoteRequestDebugStubs.SUCCESS)
val prod = NoteDebug(mode = NoteRequestDebugMode.PROD)

val someCreateNote = NoteObject(
    title = "Заметка 1",
    description = "Сделать все задачи до завтра",
    timeCreate = "2023-04-01T10:00:00Z",
    email = "test@mail.ru",
    timeReminder = "2023-04-01T11:00:00Z"
)
