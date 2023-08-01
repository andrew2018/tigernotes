package ru.otus.tigernotes.common

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.UInt.Companion.MIN_VALUE

private val INSTANT_NONE = Instant.fromEpochMilliseconds(MIN_VALUE.toLong())
private val LOCAL_DATE_NONE = Instant.fromEpochMilliseconds(MIN_VALUE.toLong()).toLocalDateTime(TimeZone.UTC).date

val Instant.Companion.NONE
    get() = INSTANT_NONE
val LocalDate.Companion.NONE
    get() = LOCAL_DATE_NONE
