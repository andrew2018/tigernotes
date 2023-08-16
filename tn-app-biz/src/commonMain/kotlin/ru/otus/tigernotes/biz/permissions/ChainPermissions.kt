package ru.otus.tigernotes.biz.permissions

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import ru.otus.tigernotes.auth.resolveChainPermissions
import ru.otus.tigernotes.common.TnContext
import ru.otus.tigernotes.common.models.TnState

fun ICorAddExecDsl<TnContext>.chainPermissions(title: String) = worker {
    this.title = title
    description = "Вычисление прав доступа для групп пользователей"

    on { state == TnState.RUNNING }

    handle {
        permissionsChain.addAll(resolveChainPermissions(principal.groups))
        println("PRINCIPAL: $principal")
        println("PERMISSIONS: $permissionsChain")
    }
}
