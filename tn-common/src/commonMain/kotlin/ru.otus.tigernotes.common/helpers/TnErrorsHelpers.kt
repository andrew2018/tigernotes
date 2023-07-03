package ru.otus.tigernotes.common.helpers

import ru.otus.tigernotes.common.TnContext
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