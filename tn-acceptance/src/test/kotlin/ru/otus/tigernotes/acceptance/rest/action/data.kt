package ru.otus.tigernotes.acceptance.rest.action

import ru.otus.tigernotes.api.models.*


val debug = NoteDebug(mode = NoteRequestDebugMode.STUB, stub = NoteRequestDebugStubs.SUCCESS)

val noteStub = NoteResponseObject(
    title = "Заметка 1",
    description = "Сделать все задачи до завтра",
    timeCreate = "2023-04-01T10:00:00Z",
    email = "test@mail.ru",
    timeReminder = "2023-04-01T11:00:00Z",
    id = "123"
)

val someCreateNote = NoteObject(
    title = "Заметка 1",
    description = "заполнить все поля",
    timeCreate = "2023-04-01T12:00:00Z",
    email = "email",
    timeReminder = "2023-04-02T12:00:00Z",
)
