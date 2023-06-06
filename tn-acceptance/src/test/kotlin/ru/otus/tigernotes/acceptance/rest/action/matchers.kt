package ru.otus.tigernotes.acceptance.rest.action

import io.kotest.matchers.Matcher
import io.kotest.matchers.MatcherResult
import io.kotest.matchers.and
import ru.otus.tigernotes.api.models.*


fun haveResult(result: ResponseResult) = Matcher<IResponse> {
    MatcherResult(
        it.result == result,
        { "actual result ${it.result} but we expected $result" },
        { "result should not be $result" }
    )
}

val haveNoErrors = Matcher<IResponse> {
    MatcherResult(
        it.errors.isNullOrEmpty(),
        { "actual errors ${it.errors} but we expected no errors" },
        { "errors should not be empty" }
    )
}

fun haveError(code: String) = haveResult(ResponseResult.ERROR)
    .and(Matcher<IResponse> {
        MatcherResult(
            it.errors?.firstOrNull { e -> e.code == code } != null,
            { "actual errors ${it.errors} but we expected error with code $code" },
            { "errors should not contain $code" }
        )
    })

val haveSuccessResult = haveResult(ResponseResult.SUCCESS) and haveNoErrors

val IResponse.note: NoteResponseObject?
    get() = when (this) {
        is NoteCreateResponse -> note
        is NoteReadResponse -> note
        is NoteUpdateResponse -> note
        is NoteDeleteResponse -> note
        else -> throw IllegalArgumentException("Invalid response type: ${this::class}")
    }

val beValidId = Matcher<String?> {
    MatcherResult(
        it != null,
        { "id should not be null" },
        { "id should be null" },
    )
}