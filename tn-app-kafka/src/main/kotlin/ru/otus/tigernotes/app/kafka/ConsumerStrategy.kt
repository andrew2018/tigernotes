package ru.otus.tigernotes.app.kafka

import ru.otus.tigernotes.api.models.IRequest
import ru.otus.tigernotes.api.models.IResponse
import ru.otus.tigernotes.api.apiRequestDeserialize
import ru.otus.tigernotes.api.apiResponseSerialize
import ru.otus.tigernotes.common.TnContext
import ru.otus.tigernotes.mappers.fromTransport
import ru.otus.tigernotes.mappers.toTransportNote


class ConsumerStrategy {
    fun topics(config: AppKafkaConfig): InputOutputTopics {
        return InputOutputTopics(config.kafkaTopicIn, config.kafkaTopicOut)
    }

    fun serialize(source: TnContext): String {
        val response: IResponse = source.toTransportNote()
        return apiResponseSerialize(response)
    }

    fun deserialize(value: String, target: TnContext) {
        val request: IRequest = apiRequestDeserialize(value)
        target.fromTransport(request)
    }
}