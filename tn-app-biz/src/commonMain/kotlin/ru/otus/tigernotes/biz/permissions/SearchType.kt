package ru.otus.tigernotes.biz.permissions

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.chain
import com.crowdproj.kotlin.cor.handlers.worker
import ru.otus.tigernotes.common.TnContext
import ru.otus.tigernotes.common.models.TnSearchPermissions
import ru.otus.tigernotes.common.models.TnState
import ru.otus.tigernotes.common.permissions.TnUserPermissions

fun ICorAddExecDsl<TnContext>.searchTypes(title: String) = chain {
    this.title = title
    description = "Добавление ограничений в поисковый запрос согласно правам доступа и др. политикам"
    on { state == TnState.RUNNING }
    worker("Определение типа поиска") {
        noteFilterValidated.searchPermissions = setOfNotNull(
            TnSearchPermissions.OWN.takeIf { permissionsChain.contains(TnUserPermissions.SEARCH_OWN) }
        ).toMutableSet()
    }
}
