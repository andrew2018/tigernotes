package ru.otus.tigernotes.stubs

import kotlinx.datetime.LocalDate
import ru.otus.tigernotes.common.models.Note
import ru.otus.tigernotes.common.models.NoteId
import ru.otus.tigernotes.stubs.NoteObjectStub.NOTE_DEFAULT

object NoteStub {
    fun get(): Note = NOTE_DEFAULT.copy()

    fun prepareResult(block: Note.() -> Unit): Note = get().apply(block)

    fun prepareSearchList(searchTitle: String, dateStart: LocalDate, dateEnd: LocalDate) = listOf(
        note(NOTE_DEFAULT, "123-01", searchTitle, dateStart, dateEnd),
        note(NOTE_DEFAULT, "123-02", searchTitle, dateStart, dateEnd),
        note(NOTE_DEFAULT, "123-03", searchTitle, dateStart, dateEnd),
        note(NOTE_DEFAULT, "123-04", searchTitle, dateStart, dateEnd),
        note(NOTE_DEFAULT, "123-05", searchTitle, dateStart, dateEnd),
        note(NOTE_DEFAULT, "123-06", searchTitle, dateStart, dateEnd)
    )

    private fun note(base: Note, id: String, searchTitle: String, dateStart: LocalDate, dateEnd: LocalDate) = base.copy(
        id = NoteId(id),
        title = "$searchTitle $id",
        description = "$dateStart $dateEnd $id",
        email = "$id@mail.ru"
    )

}
