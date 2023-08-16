package ru.otus.tigernotes.common.models

import kotlinx.datetime.Instant
import ru.otus.tigernotes.common.NONE
import ru.otus.tigernotes.common.permissions.TnPrincipalRelations

data class Note(
    var id: NoteId = NoteId.NONE,
    var title: String = "",
    var description: String = "",
    var ownerId: TnUserId = TnUserId.NONE,
    var timeCreate: Instant = Instant.NONE,
    var email: String = "",
    var timeReminder: Instant = Instant.NONE,
    var lock: NoteLock = NoteLock.NONE,
    var principalRelations: Set<TnPrincipalRelations> = emptySet(),
    val permissionsClient: MutableSet<NotePermissionClient> = mutableSetOf()
) {
    fun deepCopy(): Note = copy(
        permissionsClient = permissionsClient.toMutableSet(),
    )

    fun isEmpty() = this == NONE

    companion object {
        val NONE = Note()
    }
}
