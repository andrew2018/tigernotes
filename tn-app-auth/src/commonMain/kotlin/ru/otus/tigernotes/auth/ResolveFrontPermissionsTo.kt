package ru.otus.tigernotes.auth

import ru.otus.tigernotes.common.models.NotePermissionClient
import ru.otus.tigernotes.common.permissions.TnPrincipalRelations
import ru.otus.tigernotes.common.permissions.TnUserPermissions

fun resolveFrontPermissions(
    permissions: Iterable<TnUserPermissions>,
    relations: Iterable<TnPrincipalRelations>,
) = mutableSetOf<NotePermissionClient>()
    .apply {
        for (permission in permissions) {
            for (relation in relations) {
                accessTable[permission]?.get(relation)?.let { this@apply.add(it) }
            }
        }
    }
    .toSet()

private val accessTable = mapOf(
    // READ
    TnUserPermissions.READ_OWN to mapOf(
        TnPrincipalRelations.OWN to NotePermissionClient.READ
    ),

    // UPDATE
    TnUserPermissions.UPDATE_OWN to mapOf(
        TnPrincipalRelations.OWN to NotePermissionClient.UPDATE
    ),

    // DELETE
    TnUserPermissions.DELETE_OWN to mapOf(
        TnPrincipalRelations.OWN to NotePermissionClient.DELETE
    )
)
