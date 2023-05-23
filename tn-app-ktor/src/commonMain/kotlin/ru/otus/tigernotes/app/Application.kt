package ru.otus.tigernotes.app

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import ru.otus.tigernotes.api.apiMapper

fun Application.module(processor: NoteProcessor = NoteProcessor()) {
    routing {
        route("app") {
            install(ContentNegotiation) {
                json(apiMapper)
            }
            note(processor)
        }
    }
}

fun main() {
    embeddedServer(CIO, port = 8080) {
        module()
    }.start(wait = true)
}
