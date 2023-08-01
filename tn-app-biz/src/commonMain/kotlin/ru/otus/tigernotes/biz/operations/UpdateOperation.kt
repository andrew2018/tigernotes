package ru.otus.tigernotes.biz.operations

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.chain
import com.crowdproj.kotlin.cor.handlers.worker
import ru.otus.tigernotes.biz.general.prepareResult
import ru.otus.tigernotes.biz.groups.stubs
import ru.otus.tigernotes.biz.repo.repoPrepareUpdate
import ru.otus.tigernotes.biz.repo.repoRead
import ru.otus.tigernotes.biz.repo.repoUpdate
import ru.otus.tigernotes.biz.validation.*
import ru.otus.tigernotes.biz.workers.*
import ru.otus.tigernotes.common.TnContext
import ru.otus.tigernotes.common.models.NoteId
import ru.otus.tigernotes.common.models.NoteLock
import ru.otus.tigernotes.common.models.TnCommand
import ru.otus.tigernotes.common.models.TnState

fun ICorAddExecDsl<TnContext>.updateOperation(titleOperation: String, command: TnCommand) = chain {
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
        worker("Очистка lock") { noteValidating.lock = NoteLock(noteValidating.lock.asString().trim()) }
        worker("Очистка заголовка") { noteValidating.title = noteValidating.title.trim() }
        worker("Очистка описания") { noteValidating.description = noteValidating.description.trim() }
        worker("Очистка почты") { noteValidating.email = noteValidating.email.trim() }
        validateIdNotEmpty("Проверка на непустой id")
        validateLockNotEmpty("Проверка на непустой lock")
        validateLockProperFormat("Проверка формата lock")
        validateTitleNotEmpty("Проверка на непустой заголовок")
        validateDescriptionNotEmpty("Проверка на непустое описание")

        finishAdValidation("Успешное завершение процедуры валидации")
    }
    chain {
        title = "Логика сохранения"
        repoRead("Чтение заметки из БД")
        repoPrepareUpdate("Подготовка заметки для обновления")
        repoUpdate("Обновление заметки в БД")
    }
    prepareResult("Подготовка ответа")

    this.title = titleOperation
    on { this.command == command && state == TnState.RUNNING }
}