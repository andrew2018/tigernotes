package ru.otus.tigernotes.biz.validation

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import ru.otus.tigernotes.common.TnContext
import ru.otus.tigernotes.common.models.TnState

fun ICorAddExecDsl<TnContext>.finishAdValidation(title: String) = worker {
    this.title = title
    on { state == TnState.RUNNING }
    handle {
        noteValidated = noteValidating
    }
}

fun ICorAddExecDsl<TnContext>.finishAdFilterValidation(title: String) = worker {
    this.title = title
    on { state == TnState.RUNNING }
    handle {
        noteFilterValidated = noteFilterValidating
    }
}
