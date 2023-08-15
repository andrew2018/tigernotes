package ru.otus.tigernotes.biz.operations

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.chain
import com.crowdproj.kotlin.cor.handlers.worker
import ru.otus.tigernotes.biz.general.prepareResult
import ru.otus.tigernotes.biz.groups.stubs
import ru.otus.tigernotes.biz.permissions.chainPermissions
import ru.otus.tigernotes.biz.permissions.frontPermissions
import ru.otus.tigernotes.biz.permissions.searchTypes
import ru.otus.tigernotes.biz.repo.repoSearch
import ru.otus.tigernotes.biz.validation.*
import ru.otus.tigernotes.biz.workers.*
import ru.otus.tigernotes.common.TnContext
import ru.otus.tigernotes.common.models.TnCommand
import ru.otus.tigernotes.common.models.TnState

fun ICorAddExecDsl<TnContext>.searchOperation(titleOperation: String, command: TnCommand) = chain {
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

        finishAdFilterValidation("Успешное завершение процедуры валидации")
    }
    chainPermissions("Вычисление разрешений для пользователя")
    searchTypes("Подготовка поискового запроса")
    repoSearch("Поиск заметки в БД по фильтру")
    frontPermissions("Вычисление пользовательских разрешений для фронтенда")
    prepareResult("Подготовка ответа")

    this.title = titleOperation
    on { this.command == command && state == TnState.RUNNING }
}