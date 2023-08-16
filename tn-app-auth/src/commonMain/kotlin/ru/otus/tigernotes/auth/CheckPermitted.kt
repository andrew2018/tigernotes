package ru.otus.tigernotes.auth

import ru.otus.tigernotes.common.models.TnCommand
import ru.otus.tigernotes.common.permissions.TnPrincipalRelations
import ru.otus.tigernotes.common.permissions.TnUserPermissions

fun checkPermitted(
    command: TnCommand,
    relations: Iterable<TnPrincipalRelations>,
    permissions: Iterable<TnUserPermissions>,
) =
    relations.asSequence().flatMap { relation ->
        permissions.map { permission ->
            AccessTableConditions(
                command = command,
                permission = permission,
                relation = relation,
            )
        }
    }.any {
        accessTable[it] != null
    }

private data class AccessTableConditions(
    val command: TnCommand,
    val permission: TnUserPermissions,
    val relation: TnPrincipalRelations
)

private val accessTable = mapOf(
    // Create
    AccessTableConditions(
        command = TnCommand.CREATE,
        permission = TnUserPermissions.CREATE_OWN,
        relation = TnPrincipalRelations.NEW,
    ) to true,

    // Read
    AccessTableConditions(
        command = TnCommand.READ,
        permission = TnUserPermissions.READ_OWN,
        relation = TnPrincipalRelations.OWN,
    ) to true,

    // Update
    AccessTableConditions(
        command = TnCommand.UPDATE,
        permission = TnUserPermissions.UPDATE_OWN,
        relation = TnPrincipalRelations.OWN,
    ) to true,

    // Delete
    AccessTableConditions(
        command = TnCommand.DELETE,
        permission = TnUserPermissions.DELETE_OWN,
        relation = TnPrincipalRelations.OWN,
    ) to true,

)
