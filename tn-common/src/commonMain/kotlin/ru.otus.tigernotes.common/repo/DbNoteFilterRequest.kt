package ru.otus.tigernotes.common.repo

import kotlinx.datetime.LocalDate
import ru.otus.tigernotes.common.NONE

data class DbNoteFilterRequest(
    val searchTitle: String = "",
    val dateStart: LocalDate = LocalDate.NONE,
    val dateEnd: LocalDate = LocalDate.NONE
)
