package ru.otus.tigernotes.common

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate

private val INSTANT_NONE = Instant.fromEpochMilliseconds(Long.MIN_VALUE)
private val LOCAL_DATE_NONE = LocalDate.fromEpochDays(Int.MIN_VALUE)

val Instant.Companion.NONE
    get() = INSTANT_NONE
val LocalDate.Companion.NONE
    get() = LOCAL_DATE_NONE
