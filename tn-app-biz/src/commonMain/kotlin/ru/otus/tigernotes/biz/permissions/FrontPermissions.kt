package ru.otus.tigernotes.biz.permissions

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import ru.otus.tigernotes.auth.resolveFrontPermissions
import ru.otus.tigernotes.auth.resolveRelationsTo
import ru.otus.tigernotes.common.TnContext
import ru.otus.tigernotes.common.models.TnState

fun ICorAddExecDsl<TnContext>.frontPermissions(title: String) = worker {
    this.title = title
    description = "Вычисление разрешений пользователей для фронтенда"

    on { state == TnState.RUNNING }

    handle {
        noteRepoDone.permissionsClient.addAll(
            resolveFrontPermissions(
                permissionsChain,
                // Повторно вычисляем отношения, поскольку они могли измениться при выполении операции
                noteRepoDone.resolveRelationsTo(principal)
            )
        )

        for (note in notesRepoDone) {
            note.permissionsClient.addAll(
                resolveFrontPermissions(
                    permissionsChain,
                    note.resolveRelationsTo(principal)
                )
            )
        }
    }
}
