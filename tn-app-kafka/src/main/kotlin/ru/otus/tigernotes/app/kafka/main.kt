package ru.otus.tigernotes.app.kafka

fun main() {
    val config = AppKafkaConfig()
    val consumer = AppKafkaConsumer(config, ConsumerStrategy())
    consumer.run()
}
