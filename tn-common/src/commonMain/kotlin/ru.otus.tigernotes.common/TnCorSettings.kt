package ru.otus.tigernotes.common

import ru.otus.tigernotes.common.repo.INoteRepository
import ru.otus.tigernotes.logging.common.TnLoggerProvider

data class TnCorSettings(
    val loggerProvider: TnLoggerProvider = TnLoggerProvider(),
    val repoStub: INoteRepository = INoteRepository.NONE,
    val repoTest: INoteRepository = INoteRepository.NONE,
    val repoProd: INoteRepository = INoteRepository.NONE,
) {
    companion object {
        val NONE = TnCorSettings()
    }
}
