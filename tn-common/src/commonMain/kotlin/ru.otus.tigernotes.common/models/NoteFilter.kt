package ru.otus.tigernotes.common.models

import kotlinx.datetime.LocalDate
import ru.otus.tigernotes.common.NONE

data class NoteFilter(
    var searchTitle: String = "",
    var ownerId: TnUserId = TnUserId.NONE,
    var dateStart: LocalDate = LocalDate.NONE,
    var dateEnd: LocalDate = LocalDate.NONE,
    var searchPermissions: MutableSet<TnSearchPermissions> = mutableSetOf(),
)
