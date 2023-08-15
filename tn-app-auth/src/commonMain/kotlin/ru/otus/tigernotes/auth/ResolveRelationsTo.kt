package ru.otus.tigernotes.auth

import ru.otus.tigernotes.common.models.Note
import ru.otus.tigernotes.common.models.NoteId
import ru.otus.tigernotes.common.permissions.TnPrincipalModel
import ru.otus.tigernotes.common.permissions.TnPrincipalRelations

fun Note.resolveRelationsTo(principal: TnPrincipalModel): Set<TnPrincipalRelations> = setOfNotNull(
    TnPrincipalRelations.NONE,
    TnPrincipalRelations.NEW.takeIf { id == NoteId.NONE },
    TnPrincipalRelations.OWN.takeIf { principal.id == ownerId }
)
