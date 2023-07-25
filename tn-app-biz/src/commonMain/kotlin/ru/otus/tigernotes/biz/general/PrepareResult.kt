package ru.otus.tigernotes.biz.general

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import ru.otus.tigernotes.common.TnContext
import ru.otus.tigernotes.common.models.TnState
import ru.otus.tigernotes.common.models.TnWorkMode

fun ICorAddExecDsl<TnContext>.prepareResult(title: String) = worker {
    this.title = title
    description = "Подготовка данных для ответа клиенту на запрос"
    on { workMode != TnWorkMode.STUB }
    handle {
        noteResponse = noteRepoDone
        notesResponse = notesRepoDone
        state = when (val st = state) {
            TnState.RUNNING -> TnState.FINISHING
            else -> st
        }
    }
}
