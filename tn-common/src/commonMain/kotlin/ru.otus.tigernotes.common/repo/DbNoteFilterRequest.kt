package ru.otus.tigernotes.common.repo

import kotlinx.datetime.LocalDate
import ru.otus.tigernotes.common.NONE
import ru.otus.tigernotes.common.models.TnUserId

data class DbNoteFilterRequest(
    val searchTitle: String = "",
    val ownerId: TnUserId = TnUserId.NONE,
    val dateStart: LocalDate = LocalDate.NONE,
    val dateEnd: LocalDate = LocalDate.NONE
)
