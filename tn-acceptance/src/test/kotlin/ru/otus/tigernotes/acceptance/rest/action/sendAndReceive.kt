package ru.otus.tigernotes.acceptance.rest.action

import mu.KotlinLogging
import ru.otus.tigernotes.acceptance.fixture.client.Client
import ru.otus.tigernotes.api.apiRequestSerialize
import ru.otus.tigernotes.api.apiResponseDeserialize
import ru.otus.tigernotes.api.models.IRequest
import ru.otus.tigernotes.api.models.IResponse

private val log = KotlinLogging.logger {}

suspend fun Client.sendAndReceive(path: String, request: IRequest): IResponse {
    val requestBody = apiRequestSerialize(request)
    log.warn { "Send to note$path\n$requestBody" }

    val responseBody = sendAndReceive(path, requestBody)
    log.warn { "Received\n$responseBody" }

    return apiResponseDeserialize(responseBody)
}