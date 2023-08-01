package ru.otus.tigernotes.biz.validation

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import kotlinx.datetime.*
import ru.otus.tigernotes.common.NONE
import ru.otus.tigernotes.common.TnContext
import ru.otus.tigernotes.common.helpers.errorValidation
import ru.otus.tigernotes.common.helpers.fail
import ru.otus.tigernotes.common.models.NoteId
import ru.otus.tigernotes.common.models.NoteLock

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

fun ICorAddExecDsl<TnContext>.validateLockNotEmpty(title: String) = worker {
    this.title = title
    on { noteValidating.lock.asString().isEmpty() }
    handle {
        fail(
            errorValidation(
                field = "lock",
                violationCode = "empty",
                description = "field must not be empty"
            )
        )
    }
}

fun ICorAddExecDsl<TnContext>.validateLockProperFormat(title: String) = worker {
    this.title = title
    val regExp = Regex("^[0-9a-zA-Z-]+$")
    on { noteValidating.lock != NoteLock.NONE && !noteValidating.lock.asString().matches(regExp) }
    handle {
        val encodedId = noteValidating.lock.asString()
        fail(
            errorValidation(
                field = "lock",
                violationCode = "badFormat",
                description = "value $encodedId must contain only"
            )
        )
    }
}

