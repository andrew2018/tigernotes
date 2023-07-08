package ru.otus.tigernotes.biz.validation

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.chain
import ru.otus.tigernotes.common.TnContext
import ru.otus.tigernotes.common.models.TnState

fun ICorAddExecDsl<TnContext>.validation(block: ICorAddExecDsl<TnContext>.() -> Unit) = chain {
    block()
    title = "Валидация"

    on { state == TnState.RUNNING }
}
