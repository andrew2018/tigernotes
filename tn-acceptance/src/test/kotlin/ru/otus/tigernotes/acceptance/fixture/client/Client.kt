package ru.otus.tigernotes.acceptance.fixture.client

/**
 * Клиент к нашему приложению в докер-композе, который умеет отправлять запрос и получать ответ.
 */
interface Client {
    suspend fun sendAndReceive(path: String, request: String): String
}