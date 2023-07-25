package ru.otus.tigernotes.app.plugins

import io.ktor.server.application.*
import ru.otus.tigernotes.biz.NoteProcessor
import ru.otus.tigernotes.app.TnAppSettings
import ru.otus.tigernotes.app.config.PgConfig
import ru.otus.tigernotes.backend.repo.sql.RepoNote
import ru.otus.tigernotes.backend.repo.sql.SqlProperties
import ru.otus.tigernotes.common.TnCorSettings
import ru.otus.tigernotes.logging.common.TnLoggerProvider
import ru.otus.tigernotes.repo.inmemory.NoteRepoInMemory

fun Application.initAppSettings(): TnAppSettings {
    val pgConfig by lazy { PgConfig(environment) }
    val corSettings = TnCorSettings(
        loggerProvider = getLoggerProviderConf(),
        repoTest = NoteRepoInMemory(),
        repoStub = NoteRepoInMemory(),
        repoProd = RepoNote(SqlProperties(
            url = pgConfig.url,
            user = pgConfig.user,
            password = pgConfig.password,
            schema = pgConfig.schema,
            dropDatabase = pgConfig.dropDatabase
        )
    ))
    return TnAppSettings(
        appUrls = environment.config.propertyOrNull("ktor.urls")?.getList() ?: emptyList(),
        corSettings = corSettings,
        processor = NoteProcessor(corSettings),
    )
}

expect fun Application.getLoggerProviderConf(): TnLoggerProvider
