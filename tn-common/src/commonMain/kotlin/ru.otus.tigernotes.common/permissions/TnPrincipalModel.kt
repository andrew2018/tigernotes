package ru.otus.tigernotes.common.permissions

import ru.otus.tigernotes.common.models.TnUserId

data class TnPrincipalModel(
    val id: TnUserId = TnUserId.NONE,
    val fname: String = "",
    val mname: String = "",
    val lname: String = "",
    val groups: Set<TnUserGroups> = emptySet()
) {
    companion object {
        val NONE = TnPrincipalModel()
    }
}
