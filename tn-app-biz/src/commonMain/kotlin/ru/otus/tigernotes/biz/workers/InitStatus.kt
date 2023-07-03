package ru.otus.tigernotes.biz.workers

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import ru.otus.tigernotes.common.TnContext
import ru.otus.tigernotes.common.models.TnState

fun ICorAddExecDsl<TnContext>.initStatus(title: String) = worker() {
    this.title = title
    on { state == TnState.NONE }
    handle { state = TnState.RUNNING }
}
