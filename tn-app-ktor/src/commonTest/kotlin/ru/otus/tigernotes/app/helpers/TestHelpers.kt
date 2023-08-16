package ru.otus.tigernotes.app.helpers

import NoteRepoStub
import ru.otus.tigernotes.app.TnAppSettings
import ru.otus.tigernotes.app.config.KtorAuthConfig
import ru.otus.tigernotes.common.TnCorSettings
import ru.otus.tigernotes.common.repo.INoteRepository
import ru.otus.tigernotes.repo.inmemory.NoteRepoInMemory

fun testSettings(repo: INoteRepository? = null) = TnAppSettings(
    corSettings = TnCorSettings(
        repoStub = NoteRepoStub(),
        repoTest = repo ?: NoteRepoInMemory(),
        repoProd = repo ?: NoteRepoInMemory(),
    ),
    auth = KtorAuthConfig.TEST
)
