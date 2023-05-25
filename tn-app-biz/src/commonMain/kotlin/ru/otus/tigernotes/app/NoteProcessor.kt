package ru.otus.tigernotes.app

import ru.otus.tigernotes.common.TnContext
import ru.otus.tigernotes.common.models.TnCommand
import ru.otus.tigernotes.common.models.TnWorkMode
import ru.otus.tigernotes.stubs.NoteStub

class NoteProcessor {
    suspend fun exec(ctx: TnContext) {
        // TODO: Rewrite temporary stub solution with BIZ
        require(ctx.workMode == TnWorkMode.STUB) {
            "Currently working only in STUB mode."
        }

        when (ctx.command) {
            TnCommand.SEARCH -> {
                ctx.notesResponse.addAll(NoteStub.prepareSearchList())
            }
            else -> {
                ctx.noteResponse = NoteStub.get()
            }
        }
    }
}
