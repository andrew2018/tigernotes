package ru.otus.tigernotes.biz.operations

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.chain
import com.crowdproj.kotlin.cor.handlers.worker
import kotlinx.datetime.Clock
import ru.otus.tigernotes.biz.general.prepareResult
import ru.otus.tigernotes.biz.groups.stubs
import ru.otus.tigernotes.biz.repo.repoCreate
import ru.otus.tigernotes.biz.repo.repoPrepareCreate
import ru.otus.tigernotes.biz.validation.*
import ru.otus.tigernotes.biz.workers.*
import ru.otus.tigernotes.common.TnContext
import ru.otus.tigernotes.common.models.NoteId
import ru.otus.tigernotes.common.models.TnCommand
import ru.otus.tigernotes.common.models.TnState

fun ICorAddExecDsl<TnContext>.createOperation(titleOperation: String, command: TnCommand) = chain {
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

        finishAdValidation("Завершение проверок")
    }
    chain {
        title = "Логика сохранения"
        repoPrepareCreate("Подготовка заметки для сохранения")
        repoCreate("Создание заметки в БД")
    }
    prepareResult("Подготовка ответа")

    this.title = titleOperation
    on { this.command == command && state == TnState.RUNNING }
}