package ru.otus.tigernotes.common

import kotlinx.datetime.Instant
import kotlinx.datetime.Instant.Companion.DISTANT_PAST
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

private val INSTANT_NONE = DISTANT_PAST
private val LOCAL_DATE_NONE = DISTANT_PAST.toLocalDateTime(TimeZone.UTC).date

val Instant.Companion.NONE
    get() = INSTANT_NONE
val LocalDate.Companion.NONE
    get() = LOCAL_DATE_NONE
