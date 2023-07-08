package ru.otus.tigernotes.app

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import ru.otus.tigernotes.api.apiMapper
import ru.otus.tigernotes.app.plugins.initAppSettings

fun Application.module(appSettings: TnAppSettings = initAppSettings()) {
    routing {
        route("app") {
            install(ContentNegotiation) {
                json(apiMapper)
            }
            note(appSettings)
        }
    }
}

fun main() {
    embeddedServer(CIO) {
        module()
    }.start(wait = true)
}
