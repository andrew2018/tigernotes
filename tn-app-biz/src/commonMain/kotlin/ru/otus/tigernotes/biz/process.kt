package ru.otus.tigernotes.biz

import kotlinx.datetime.Clock
import ru.otus.tigernotes.common.TnContext
import ru.otus.tigernotes.common.helpers.asTnError
import ru.otus.tigernotes.common.helpers.fail
import ru.otus.tigernotes.common.models.TnCommand
import ru.otus.tigernotes.logging.common.ITnLogWrapper

suspend fun <T> NoteProcessor.process(
    logger: ITnLogWrapper,
    logId: String,
    command: TnCommand,
    fromTransport: suspend (TnContext) -> Unit,
    sendResponse: suspend (TnContext) -> T,
    toLog: TnContext.(logId: String) -> Any): T {

    val ctx = TnContext(
        timeStart = Clock.System.now(),
    )
    var realCommand = command

    return try {
        logger.doWithLogging(id = logId) {
            fromTransport(ctx)
            realCommand = ctx.command

            logger.info(
                msg = "$realCommand request is got",
                data = ctx.toLog("${logId}-got")
            )
            exec(ctx)
            logger.info(
                msg = "$realCommand request is handled",
                data = ctx.toLog("${logId}-handled")
            )
            sendResponse(ctx)
        }
    } catch (e: Throwable) {
        logger.doWithLogging(id = "${logId}-failure") {
            logger.error(msg = "$realCommand handling failed")

            ctx.command = realCommand
            ctx.fail(e.asTnError())
            exec(ctx)
            sendResponse(ctx)
        }
    }
}