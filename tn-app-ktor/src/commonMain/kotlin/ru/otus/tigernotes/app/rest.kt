package ru.otus.tigernotes.app

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Route.note(processor: NoteProcessor) {
    route("note") {
        post("create") {
            call.createNote(processor)
        }
        post("read") {
            call.readNote(processor)
        }
        post("update") {
            call.updateNote(processor)
        }
        post("delete") {
            call.deleteNote(processor)
        }
        post("search") {
            call.searchNote(processor)
        }
    }
}
