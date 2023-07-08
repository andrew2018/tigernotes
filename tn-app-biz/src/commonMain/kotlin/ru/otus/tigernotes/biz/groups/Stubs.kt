package ru.otus.tigernotes.biz.groups

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.chain
import ru.otus.tigernotes.common.TnContext
import ru.otus.tigernotes.common.models.TnState
import ru.otus.tigernotes.common.models.TnWorkMode

fun ICorAddExecDsl<TnContext>.stubs(title: String, block: ICorAddExecDsl<TnContext>.() -> Unit) = chain {
    block()
    this.title = title
    on { workMode == TnWorkMode.STUB && state == TnState.RUNNING }
}
