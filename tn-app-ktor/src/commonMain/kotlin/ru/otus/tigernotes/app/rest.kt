package ru.otus.tigernotes.app

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Route.note(appSettings: TnAppSettings) {
    val loggerNote = appSettings.corSettings.loggerProvider.logger(Route::note)
    route("note") {
        post("create") {
            call.createNote(appSettings, loggerNote)
        }
        post("read") {
            call.readNote(appSettings, loggerNote)
        }
        post("update") {
            call.updateNote(appSettings, loggerNote)
        }
        post("delete") {
            call.deleteNote(appSettings, loggerNote)
        }
        post("search") {
            call.searchNote(appSettings, loggerNote)
        }
    }
}
