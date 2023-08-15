package ru.otus.tigernotes.app.base

import io.ktor.server.auth.jwt.*
import ru.otus.tigernotes.app.config.KtorAuthConfig.Companion.F_NAME_CLAIM
import ru.otus.tigernotes.app.config.KtorAuthConfig.Companion.GROUPS_CLAIM
import ru.otus.tigernotes.app.config.KtorAuthConfig.Companion.ID_CLAIM
import ru.otus.tigernotes.app.config.KtorAuthConfig.Companion.L_NAME_CLAIM
import ru.otus.tigernotes.app.config.KtorAuthConfig.Companion.M_NAME_CLAIM
import ru.otus.tigernotes.common.models.TnUserId
import ru.otus.tigernotes.common.permissions.TnPrincipalModel
import ru.otus.tigernotes.common.permissions.TnUserGroups

fun JWTPrincipal?.toModel() = this?.run {
    TnPrincipalModel(
        id = payload.getClaim(ID_CLAIM).asString()?.let { TnUserId(it) } ?: TnUserId.NONE,
        fname = payload.getClaim(F_NAME_CLAIM).asString() ?: "",
        mname = payload.getClaim(M_NAME_CLAIM).asString() ?: "",
        lname = payload.getClaim(L_NAME_CLAIM).asString() ?: "",
        groups = payload
            .getClaim(GROUPS_CLAIM)
            ?.asList(String::class.java)
            ?.mapNotNull {
                when(it) {
                    "USER" -> TnUserGroups.USER
                    else -> null
                }
            }?.toSet() ?: emptySet()
    )
} ?: TnPrincipalModel.NONE
