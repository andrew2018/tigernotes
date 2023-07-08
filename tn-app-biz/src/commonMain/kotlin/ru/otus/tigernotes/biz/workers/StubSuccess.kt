package ru.otus.tigernotes.biz.workers

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import kotlinx.datetime.Instant
import ru.otus.tigernotes.common.NONE
import ru.otus.tigernotes.common.TnContext
import ru.otus.tigernotes.common.models.NoteId
import ru.otus.tigernotes.common.models.TnState
import ru.otus.tigernotes.common.stubs.TnStubs
import ru.otus.tigernotes.stubs.NoteStub

fun ICorAddExecDsl<TnContext>.stubCreateSuccess(title: String) = worker {
    this.title = title
    on { stubCase == TnStubs.SUCCESS && state == TnState.RUNNING }
    handle {
        state = TnState.FINISHING
        val stub = NoteStub.prepareResult {
            note.title.takeIf { it.isNotBlank() }?.also { this.title = it }
            note.description.takeIf { it.isNotBlank() }?.also { this.description = it }
            note.timeCreate.takeIf { it != Instant.NONE }?.also { this.timeCreate = it }
            note.email.takeIf { it.isNotBlank() }?.also { this.email = it }
            note.timeReminder.takeIf { it != Instant.NONE }?.also { this.timeReminder = it }
        }
        noteResponse = stub
    }
}

fun ICorAddExecDsl<TnContext>.stubReadSuccess(title: String) = worker {
    this.title = title
    on { stubCase == TnStubs.SUCCESS && state == TnState.RUNNING }
    handle {
        state = TnState.FINISHING
        val stub = NoteStub.prepareResult {
            note.title.takeIf { it.isNotBlank() }?.also { this.title = it }
        }
        noteResponse = stub
    }
}

fun ICorAddExecDsl<TnContext>.stubUpdateSuccess(title: String) = worker {
    this.title = title
    on { stubCase == TnStubs.SUCCESS && state == TnState.RUNNING }
    handle {
        state = TnState.FINISHING
        val stub = NoteStub.prepareResult {
            note.id.takeIf { it != NoteId.NONE }?.also { this.id = it }
            note.title.takeIf { it.isNotBlank() }?.also { this.title = it }
            note.description.takeIf { it.isNotBlank() }?.also { this.description = it }
            note.timeCreate.takeIf { it != Instant.NONE }?.also { this.timeCreate = it }
            note.email.takeIf { it.isNotBlank() }?.also { this.email = it }
            note.timeReminder.takeIf { it != Instant.NONE }?.also { this.timeReminder = it }
        }
        noteResponse = stub
    }
}

fun ICorAddExecDsl<TnContext>.stubDeleteSuccess(title: String) = worker {
    this.title = title
    on { stubCase == TnStubs.SUCCESS && state == TnState.RUNNING }
    handle {
        state = TnState.FINISHING
        val stub = NoteStub.prepareResult {
            note.title.takeIf { it.isNotBlank() }?.also { this.title = it }
        }
        noteResponse = stub
    }
}

fun ICorAddExecDsl<TnContext>.stubSearchSuccess(title: String) = worker {
    this.title = title
    on { stubCase == TnStubs.SUCCESS && state == TnState.RUNNING }
    handle {
        state = TnState.FINISHING
        notesResponse.addAll(NoteStub.prepareSearchList(noteFilter.searchTitle, noteFilter.dateStart, noteFilter.dateEnd))
    }
}