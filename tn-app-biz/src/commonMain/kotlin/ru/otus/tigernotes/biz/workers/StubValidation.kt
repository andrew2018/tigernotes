package ru.otus.tigernotes.biz.workers

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import ru.otus.tigernotes.common.TnContext
import ru.otus.tigernotes.common.helpers.fail
import ru.otus.tigernotes.common.models.TnError
import ru.otus.tigernotes.common.models.TnState
import ru.otus.tigernotes.common.stubs.TnStubs

fun ICorAddExecDsl<TnContext>.stubValidationBadId(title: String) = worker {
    this.title = title
    on { stubCase == TnStubs.BAD_ID && state == TnState.RUNNING }
    handle {
        state = TnState.FAILING
        this.errors.add(
            TnError(
                group = "validation",
                code = "validation-id",
                field = "id",
                message = "Wrong id field"
            )
        )
    }
}

fun ICorAddExecDsl<TnContext>.stubValidationBadTitle(title: String) = worker {
    this.title = title
    on { stubCase == TnStubs.BAD_TITLE && state == TnState.RUNNING }
    handle {
        state = TnState.FAILING
        this.errors.add(
            TnError(
                group = "validation",
                code = "validation-title",
                field = "title",
                message = "Wrong title field"
            )
        )
    }
}

fun ICorAddExecDsl<TnContext>.stubValidationBadDescription(title: String) = worker {
    this.title = title
    on { stubCase == TnStubs.BAD_DESCRIPTION && state == TnState.RUNNING }
    handle {
        state = TnState.FAILING
        this.errors.add(
            TnError(
                group = "validation",
                code = "validation-description",
                field = "description",
                message = "Wrong description field"
            )
        )
    }
}

fun ICorAddExecDsl<TnContext>.stubValidationBadEmail(title: String) = worker {
    this.title = title
    on { stubCase == TnStubs.BAD_EMAIL && state == TnState.RUNNING }
    handle {
        state = TnState.FAILING
        this.errors.add(
            TnError(
                group = "validation",
                code = "validation-email",
                field = "email",
                message = "Wrong email field"
            )
        )
    }
}

fun ICorAddExecDsl<TnContext>.stubValidationBadTimeReminder(title: String) = worker {
    this.title = title
    on { stubCase == TnStubs.BAD_TIME_REMINDER && state == TnState.RUNNING }
    handle {
        state = TnState.FAILING
        this.errors.add(
            TnError(
                group = "validation",
                code = "validation-timeReminder",
                field = "email",
                message = "Wrong timeReminder field"
            )
        )
    }
}

fun ICorAddExecDsl<TnContext>.stubValidationBadSearchTitle(title: String) = worker {
    this.title = title
    on { stubCase == TnStubs.BAD_SEARCH_TITLE && state == TnState.RUNNING }
    handle {
        state = TnState.FAILING
        this.errors.add(
            TnError(
                group = "validation",
                code = "validation-searchTitle",
                field = "email",
                message = "Wrong searchTitle field"
            )
        )
    }
}

fun ICorAddExecDsl<TnContext>.stubValidationBadSearchDateStart(title: String) = worker {
    this.title = title
    on { stubCase == TnStubs.BAD_SEARCH_DATE_START && state == TnState.RUNNING }
    handle {
        state = TnState.FAILING
        this.errors.add(
            TnError(
                group = "validation",
                code = "validation-searchDateStart",
                field = "email",
                message = "Wrong searchDateStart field"
            )
        )
    }
}

fun ICorAddExecDsl<TnContext>.stubValidationBadSearchDateEnd(title: String) = worker {
    this.title = title
    on { stubCase == TnStubs.BAD_SEARCH_DATE_END && state == TnState.RUNNING }
    handle {
        state = TnState.FAILING
        this.errors.add(
            TnError(
                group = "validation",
                code = "validation-searchDateEnd",
                field = "email",
                message = "Wrong searchDateEnd field"
            )
        )
    }
}

fun ICorAddExecDsl<TnContext>.stubDbError(title: String) = worker {
    this.title = title
    on { stubCase == TnStubs.DB_ERROR && state == TnState.RUNNING }
    handle {
        state = TnState.FAILING
        this.errors.add(
            TnError(
                group = "internal",
                code = "internal-db",
                message = "Internal error"
            )
        )
    }
}

fun ICorAddExecDsl<TnContext>.stubNoCase(title: String) = worker {
    this.title = title
    on { state == TnState.RUNNING }
    handle {
        fail(
            TnError(
                code = "validation",
                field = "stub",
                group = "validation",
                message = "Wrong stub case is requested: ${stubCase.name}"
            )
        )
    }
}