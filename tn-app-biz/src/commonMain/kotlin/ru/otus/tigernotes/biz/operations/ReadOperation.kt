package ru.otus.tigernotes.biz.operations

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.chain
import com.crowdproj.kotlin.cor.handlers.worker
import ru.otus.tigernotes.biz.general.prepareResult
import ru.otus.tigernotes.biz.groups.stubs
import ru.otus.tigernotes.biz.permissions.accessValidation
import ru.otus.tigernotes.biz.permissions.chainPermissions
import ru.otus.tigernotes.biz.permissions.frontPermissions
import ru.otus.tigernotes.biz.repo.repoRead
import ru.otus.tigernotes.biz.validation.*
import ru.otus.tigernotes.biz.workers.*
import ru.otus.tigernotes.common.TnContext
import ru.otus.tigernotes.common.models.NoteId
import ru.otus.tigernotes.common.models.TnCommand
import ru.otus.tigernotes.common.models.TnState

fun ICorAddExecDsl<TnContext>.readOperation(titleOperation: String, command: TnCommand) = chain {
    stubs("Обработка стабов") {
        stubReadSuccess("Имитация успешной обработки")
        stubValidationBadId("Имитация ошибки валидации id")
        stubDbError("Имитация ошибки работы с БД")
        stubNoCase("Ошибка: запрошенный стаб недопустим")
    }
    validation {
        worker("Копируем поля в noteValidating") { noteValidating = note.deepCopy() }
        worker("Очистка id") { noteValidating.id = NoteId(noteValidating.id.asString().trim()) }
        validateIdNotEmpty("Проверка на непустой id")
        finishAdValidation("Успешное завершение процедуры валидации")
    }
    chainPermissions("Вычисление разрешений для пользователя")
    chain {
        title = "Логика чтения"
        repoRead("Чтение заметки из БД")
        accessValidation("Вычисление прав доступа")
        worker {
            title = "Подготовка ответа для Read"
            on { state == TnState.RUNNING }
            handle { noteRepoDone = noteRepoRead }
        }
    }
    frontPermissions("Вычисление пользовательских разрешений для фронтенда")
    prepareResult("Подготовка ответа")

    this.title = titleOperation
    on { this.command == command && state == TnState.RUNNING }
}