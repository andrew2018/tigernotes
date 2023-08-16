package ru.otus.tigernotes.app.plugins

import io.ktor.server.application.*
import ru.otus.tigernotes.biz.NoteProcessor
import ru.otus.tigernotes.app.TnAppSettings
import ru.otus.tigernotes.app.config.KtorAuthConfig
import ru.otus.tigernotes.app.config.PgConfig
import ru.otus.tigernotes.backend.repo.sql.RepoNote
import ru.otus.tigernotes.backend.repo.sql.SqlProperties
import ru.otus.tigernotes.common.TnCorSettings
import ru.otus.tigernotes.common.repo.INoteRepository
import ru.otus.tigernotes.logging.common.TnLoggerProvider
import ru.otus.tigernotes.repo.inmemory.NoteRepoInMemory

fun Application.initAppSettings(): TnAppSettings {
    val pgConfig by lazy { PgConfig(environment) }
    var repo: INoteRepository = NoteRepoInMemory()
    var auth = KtorAuthConfig.TEST
    if (environment.config.property("ktor.development").getString() == "false") {
        repo = initAppPg(pgConfig)
        auth = initAppAuth()
    }
    val corSettings = TnCorSettings(
        loggerProvider = getLoggerProviderConf(),
        repoTest = NoteRepoInMemory(),
        repoStub = NoteRepoInMemory(),
        repoProd = repo
    )
    return TnAppSettings(
        appUrls = environment.config.propertyOrNull("ktor.urls")?.getList() ?: emptyList(),
        corSettings = corSettings,
        processor = NoteProcessor(corSettings),
        auth = auth,
    )
}

expect fun Application.getLoggerProviderConf(): TnLoggerProvider

private fun initAppPg(pgConfig: PgConfig): RepoNote = RepoNote(SqlProperties(
    url = pgConfig.url,
    user = pgConfig.user,
    password = pgConfig.password,
    schema = pgConfig.schema,
    dropDatabase = pgConfig.dropDatabase
))
private fun Application.initAppAuth(): KtorAuthConfig = KtorAuthConfig(
    secret = environment.config.propertyOrNull("jwt.secret")?.getString() ?: "",
    issuer = environment.config.property("jwt.issuer").getString(),
    audience = environment.config.property("jwt.audience").getString(),
    realm = environment.config.property("jwt.realm").getString(),
    clientId = environment.config.property("jwt.clientId").getString(),
    certUrl = environment.config.propertyOrNull("jwt.certUrl")?.getString(),
)