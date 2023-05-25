package ru.otus.tigernotes.stubs

import ru.otus.tigernotes.common.models.Note
import ru.otus.tigernotes.common.models.NoteId
import ru.otus.tigernotes.stubs.NoteObjectStub.NOTE_DEFAULT

object NoteStub {
    fun get(): Note = NOTE_DEFAULT.copy()

    fun prepareSearchList() = listOf(
        note(NOTE_DEFAULT, "123-01"),
        note(NOTE_DEFAULT, "123-02"),
        note(NOTE_DEFAULT, "123-03"),
        note(NOTE_DEFAULT, "123-04"),
        note(NOTE_DEFAULT, "123-05"),
        note(NOTE_DEFAULT, "123-06")
    )

    private fun note(base: Note, id: String) = base.copy(
        id = NoteId(id),
        title = "title $id",
        description = "desc $id",
        email = "$id@mail.ru"
    )

}
