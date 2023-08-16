package ru.otus.tigernotes.biz

import ru.otus.tigernotes.common.TnContext
import ru.otus.tigernotes.common.models.TnUserId
import ru.otus.tigernotes.common.permissions.TnPrincipalModel
import ru.otus.tigernotes.common.permissions.TnUserGroups

fun TnContext.addTestPrincipal(userId: TnUserId = TnUserId("321")) {
    principal = TnPrincipalModel(
        id = userId,
        groups = setOf(
            TnUserGroups.USER,
            TnUserGroups.TEST,
        )
    )
}
