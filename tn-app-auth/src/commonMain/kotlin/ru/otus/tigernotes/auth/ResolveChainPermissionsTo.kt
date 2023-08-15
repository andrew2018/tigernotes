package ru.otus.tigernotes.auth

import ru.otus.tigernotes.common.permissions.TnUserGroups
import ru.otus.tigernotes.common.permissions.TnUserPermissions

fun resolveChainPermissions(
    groups: Iterable<TnUserGroups>,
) = mutableSetOf<TnUserPermissions>()
    .apply {
        addAll(groups.flatMap { groupPermissionsAdmits[it] ?: emptySet() })
        removeAll(groups.flatMap { groupPermissionsDenys[it] ?: emptySet() }.toSet())
    }
    .toSet()

private val groupPermissionsAdmits = mapOf(
    TnUserGroups.USER to setOf(
        TnUserPermissions.READ_OWN,
        TnUserPermissions.CREATE_OWN,
        TnUserPermissions.UPDATE_OWN,
        TnUserPermissions.DELETE_OWN
    ),
    TnUserGroups.TEST to setOf(),
    TnUserGroups.BAN_NOTE to setOf(),
)

private val groupPermissionsDenys = mapOf(
    TnUserGroups.USER to setOf(),
    TnUserGroups.TEST to setOf(),
    TnUserGroups.BAN_NOTE to setOf(
        TnUserPermissions.UPDATE_OWN,
        TnUserPermissions.CREATE_OWN,
    ),
)
