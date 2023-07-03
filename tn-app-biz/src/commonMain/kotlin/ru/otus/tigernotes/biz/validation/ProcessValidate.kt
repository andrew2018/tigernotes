package ru.otus.tigernotes.biz.validation

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import kotlinx.datetime.*
import ru.otus.tigernotes.common.NONE
import ru.otus.tigernotes.common.TnContext
import ru.otus.tigernotes.common.helpers.errorValidation
import ru.otus.tigernotes.common.helpers.fail
import ru.otus.tigernotes.common.models.NoteId

fun ICorAddExecDsl<TnContext>.validateIdNotEmpty(title: String) = worker {
    this.title = title
    on { noteValidating.id.asString().isEmpty() }
    handle {
        fail(
            errorValidation(
                field = "id",
                violationCode = "empty",
                description = "field must not be empty"
            )
        )
    }
}

fun ICorAddExecDsl<TnContext>.validateTitleNotEmpty(title: String) = worker {
    this.title = title
    on { noteValidating.title.isEmpty() }
    handle {
        fail(
            errorValidation(
                field = "title",
                violationCode = "empty",
                description = "field must not be empty"
            )
        )
    }
}

fun ICorAddExecDsl<TnContext>.validateDescriptionNotEmpty(title: String) = worker {
    this.title = title
    on { noteValidating.description.isEmpty() }
    handle {
        fail(
            errorValidation(
                field = "description",
                violationCode = "empty",
                description = "field must not be empty"
            )
        )
    }
}

fun ICorAddExecDsl<TnContext>.validateEmailNotEmpty(title: String) = worker {
    this.title = title
    on { noteValidating.email.isEmpty() }
    handle {
        fail(
            errorValidation(
                field = "email",
                violationCode = "empty",
                description = "field must not be empty"
            )
        )
    }
}

fun ICorAddExecDsl<TnContext>.validateEmailValid(title: String) = worker {
    this.title = title
    val regExp = Regex("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}\$")
    on { noteValidating.email != "" && !noteValidating.email.matches(regExp) }
    handle {
        fail(
            errorValidation(
                field = "email",
                violationCode = "valid",
                description = "field must be valid"
            )
        )
    }
}

fun ICorAddExecDsl<TnContext>.validateTimeReminderCorrect(title: String) = worker {
    this.title = title
    on { noteValidating.timeReminder < Clock.System.now() }
    handle {
        fail(
            errorValidation(
                field = "timeReminder",
                violationCode = "correct",
                description = "field must be more current"
            )
        )
    }
}

fun ICorAddExecDsl<TnContext>.validateSearchTitleNotEmpty(title: String) = worker {
    this.title = title
    on { noteFilterValidating.searchTitle.isEmpty() }
    handle {
        fail(
            errorValidation(
                field = "searchTitle",
                violationCode = "empty",
                description = "field must not be empty"
            )
        )
    }
}

fun ICorAddExecDsl<TnContext>.validateDateStartNotNone(title: String) = worker {
    this.title = title
    on { noteFilterValidating.dateStart == LocalDate.NONE }
    handle {
        fail(
            errorValidation(
                field = "dateStart",
                violationCode = "none",
                description = "field must not be none"
            )
        )
    }
}

fun ICorAddExecDsl<TnContext>.validateDateEndNotNone(title: String) = worker {
    this.title = title
    on { noteFilterValidating.dateEnd == LocalDate.NONE }
    handle {
        fail(
            errorValidation(
                field = "dateEnd",
                violationCode = "none",
                description = "field must not be none"
            )
        )
    }
}
