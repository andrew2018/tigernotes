package ru.otus.tigernotes.biz.general

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import ru.otus.tigernotes.common.TnContext
import ru.otus.tigernotes.common.helpers.errorAdministration
import ru.otus.tigernotes.common.helpers.fail
import ru.otus.tigernotes.common.models.TnWorkMode
import ru.otus.tigernotes.common.permissions.TnUserGroups
import ru.otus.tigernotes.common.repo.INoteRepository

fun ICorAddExecDsl<TnContext>.initRepo(title: String) = worker {
    this.title = title
    description = """
        Вычисление основного рабочего репозитория в зависимости от запрошенного режима работы        
    """.trimIndent()
    handle {
        noteRepo = when {
            workMode == TnWorkMode.TEST -> settings.repoTest
            workMode == TnWorkMode.STUB -> settings.repoStub
            principal.groups.contains(TnUserGroups.TEST) -> settings.repoTest
            else -> settings.repoProd
        }
        if (workMode != TnWorkMode.STUB && noteRepo == INoteRepository.NONE) fail(
            errorAdministration(
                field = "repo",
                violationCode = "dbNotConfigured",
                description = "The database is unconfigured for chosen workmode ($workMode). " +
                        "Please, contact the administrator staff"
            )
        )
    }
}
