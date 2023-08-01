package ru.otus.tigernotes.common.repo

import ru.otus.tigernotes.common.models.TnError

interface IDbResponse<T> {
    val data: T?
    val isSuccess: Boolean
    val errors: List<TnError>
}
