package ru.otus.tigernotes.common.helpers

import ru.otus.tigernotes.common.TnContext
import ru.otus.tigernotes.common.exceptions.RepoConcurrencyException
import ru.otus.tigernotes.common.models.NoteLock
import ru.otus.tigernotes.common.models.TnError
import ru.otus.tigernotes.common.models.TnState

fun TnContext.addError(vararg error: TnError) = errors.addAll(error)

fun TnContext.fail(error: TnError) {
    addError(error)
    state = TnState.FAILING
}

fun Throwable.asTnError(
    code: String = "unknown",
    group: String = "exceptions",
    message: String = this.message ?: "",
) = TnError(
    code = code,
    group = group,
    field = "",
    message = message,
    exception = this,
)

fun errorValidation(
    field: String,
    /**
     * Код, характеризующий ошибку. Не должен включать имя поля или указание на валидацию.
     * Например: empty, badSymbols, tooLong, etc
     */
    violationCode: String,
    description: String,
    level: TnError.Level = TnError.Level.ERROR,
) = TnError(
    code = "validation-$field-$violationCode",
    field = field,
    group = "validation",
    message = "Validation error for field $field: $description",
    level = level,
)

fun errorAdministration(
    /**
     * Код, характеризующий ошибку. Не должен включать имя поля или указание на валидацию.
     * Например: empty, badSymbols, tooLong, etc
     */
    field: String = "",
    violationCode: String,
    description: String,
    exception: Exception? = null,
    level: TnError.Level = TnError.Level.ERROR,
) = TnError(
    field = field,
    code = "administration-$violationCode",
    group = "administration",
    message = "Microservice management error: $description",
    level = level,
    exception = exception
)

fun errorRepoConcurrency(
    expectedLock: NoteLock?,
    actualLock: NoteLock?,
    exception: Exception? = null,
) = TnError(
    field = "lock",
    code = "concurrency",
    group = "repo",
    message = "The object has been changed concurrently by another user or process",
    exception = exception ?: RepoConcurrencyException(expectedLock, actualLock),
)

val errorNotFound = TnError(
    field = "id",
    message = "Not Found",
    code = "not-found"
)

val errorEmptyId = TnError(
    field = "id",
    message = "Id must not be null or blank"
)