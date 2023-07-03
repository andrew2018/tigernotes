package ru.otus.tigernotes.biz.groups

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.chain
import com.crowdproj.kotlin.cor.handlers.worker
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import ru.otus.tigernotes.biz.validation.*
import ru.otus.tigernotes.biz.workers.*
import ru.otus.tigernotes.common.NONE
import ru.otus.tigernotes.common.TnContext
import ru.otus.tigernotes.common.models.NoteId
import ru.otus.tigernotes.common.models.TnCommand
import ru.otus.tigernotes.common.models.TnState

fun ICorAddExecDsl<TnContext>.createOperation(title: String, command: TnCommand) = chain {
    stubs("Обработка стабов") {
        stubCreateSuccess("Имитация успешной обработки")
        stubValidationBadTitle("Имитация ошибки валидации заголовка")
        stubValidationBadDescription("Имитация ошибки валидации описания")
        stubValidationBadEmail("Имитация ошибки валидации почты")
        stubValidationBadTimeReminder("Имитация ошибки валидации времени напоминания")
        stubDbError("Имитация ошибки работы с БД")
        stubNoCase("Ошибка: запрошенный стаб недопустим")
    }
    validation {
        worker("Копируем поля в noteValidating") { noteValidating = note.copy() }
        worker("Очистка id") { noteValidating.id = NoteId.NONE }
        worker("Очистка заголовка") { noteValidating.title = noteValidating.title.trim() }
        worker("Очистка описания") { noteValidating.description = noteValidating.description.trim() }
        worker("Очистка почты") { noteValidating.email = noteValidating.email.trim() }
        validateTitleNotEmpty("Проверка, что заголовок не пуст")
        validateDescriptionNotEmpty("Проверка, что описание не пусто")
        validateEmailNotEmpty("Проверка, что почта не пустая")
        validateEmailValid("Проверка, что почта корректная")
        validateTimeReminderCorrect("Проверка, что время напоминания установлено корретное")

        finishAdValidation("Завершение проверок")
    }

    this.title = title
    on { this.command == command && state == TnState.RUNNING }
}

fun ICorAddExecDsl<TnContext>.readOperation(title: String, command: TnCommand) = chain {
    stubs("Обработка стабов") {
        stubReadSuccess("Имитация успешной обработки")
        stubValidationBadId("Имитация ошибки валидации id")
        stubDbError("Имитация ошибки работы с БД")
        stubNoCase("Ошибка: запрошенный стаб недопустим")
    }
    validation {
        worker("Копируем поля в noteValidating") { noteValidating = note.copy() }
        worker("Очистка id") { noteValidating.id = NoteId(noteValidating.id.asString().trim()) }
        validateIdNotEmpty("Проверка на непустой id")
        finishAdValidation("Успешное завершение процедуры валидации")
    }

    this.title = title
    on { this.command == command && state == TnState.RUNNING }
}

fun ICorAddExecDsl<TnContext>.updateOperation(title: String, command: TnCommand) = chain {
    stubs("Обработка стабов") {
        stubUpdateSuccess("Имитация успешной обработки")
        stubValidationBadId("Имитация ошибки валидации id")
        stubValidationBadTitle("Имитация ошибки валидации заголовка")
        stubValidationBadDescription("Имитация ошибки валидации описания")
        stubValidationBadEmail("Имитация ошибки валидации почты")
        stubValidationBadTimeReminder("Имитация ошибки валидации времени напоминания")
        stubDbError("Имитация ошибки работы с БД")
        stubNoCase("Ошибка: запрошенный стаб недопустим")
    }
    validation {
        worker("Копируем поля в noteValidating") { noteValidating = note.copy() }
        worker("Очистка id") { noteValidating.id = NoteId(noteValidating.id.asString().trim()) }
        worker("Очистка заголовка") { noteValidating.title = noteValidating.title.trim() }
        worker("Очистка описания") { noteValidating.description = noteValidating.description.trim() }
        worker("Очистка почты") { noteValidating.email = noteValidating.email.trim() }
        validateIdNotEmpty("Проверка на непустой id")
        validateTitleNotEmpty("Проверка на непустой заголовок")
        validateDescriptionNotEmpty("Проверка на непустое описание")
        validateEmailNotEmpty("Проверка, что почта не пустая")
        validateEmailValid("Проверка, что почта корректная")
        validateTimeReminderCorrect("Проверка, что время напоминания установлено корретное")

        finishAdValidation("Успешное завершение процедуры валидации")
    }

    this.title = title
    on { this.command == command && state == TnState.RUNNING }
}

fun ICorAddExecDsl<TnContext>.deleteOperation(title: String, command: TnCommand) = chain {
    stubs("Обработка стабов") {
        stubDeleteSuccess("Имитация успешной обработки")
        stubValidationBadId("Имитация ошибки валидации id")
        stubDbError("Имитация ошибки работы с БД")
        stubNoCase("Ошибка: запрошенный стаб недопустим")
    }
    validation {
        worker("Копируем поля в noteValidating") { noteValidating = note.copy() }
        worker("Очистка id") { noteValidating.id = NoteId(noteValidating.id.asString().trim()) }
        validateIdNotEmpty("Проверка на непустой id")
        finishAdValidation("Успешное завершение процедуры валидации")
    }

    this.title = title
    on { this.command == command && state == TnState.RUNNING }
}

fun ICorAddExecDsl<TnContext>.searchOperation(title: String, command: TnCommand) = chain {
    stubs("Обработка стабов") {
        stubSearchSuccess("Имитация успешной обработки")
        stubValidationBadSearchTitle("Имитация ошибки валидации поиска по заголовку")
        stubValidationBadSearchDateStart("Имитация ошибки валидации поиска по начальной дате создания заявки")
        stubValidationBadSearchDateEnd("Имитация ошибки валидации поиска по конечной дате создания заявки")
        stubValidationBadId("Имитация ошибки валидации id")
        stubDbError("Имитация ошибки работы с БД")
        stubNoCase("Ошибка: запрошенный стаб недопустим")
    }
    validation {
        worker("Копируем поля в adFilterValidating") { noteFilterValidating = noteFilter.copy() }
        worker("Очистка заголовка для поиска") { noteFilterValidating.searchTitle.trim() }
        validateSearchTitleNotEmpty("Проверка на непустой заголовок поиска")
        validateDateStartNotNone("Проверка на непустой заголовок поиска")
        validateDateEndNotNone("Проверка на непустой заголовок поиска")

        finishAdFilterValidation("Успешное завершение процедуры валидации")
    }

    this.title = title
    on { this.command == command && state == TnState.RUNNING }
}