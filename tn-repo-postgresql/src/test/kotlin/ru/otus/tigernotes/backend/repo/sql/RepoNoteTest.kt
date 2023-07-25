package ru.otus.tigernotes.backend.repo.sql

import ru.otus.tigernotes.common.repo.INoteRepository
import ru.otus.tigernotes.repo.tests.*

class RepoNoteSQLCreateTest : RepoNoteCreateTest() {
    override val repo: INoteRepository = SqlTestCompanion.repoUnderTestContainer(
        initObjects,
        randomUuid = { lockNew.asString() },
    )
}

class RepoAdSQLDeleteTest : RepoNoteDeleteTest() {
    override val repo: INoteRepository = SqlTestCompanion.repoUnderTestContainer(initObjects)
}

class RepoAdSQLReadTest : RepoNoteReadTest() {
    override val repo: INoteRepository = SqlTestCompanion.repoUnderTestContainer(initObjects)
}

class RepoAdSQLSearchTest : RepoNoteSearchTest() {
    override val repo: INoteRepository = SqlTestCompanion.repoUnderTestContainer(initObjects)
}

class RepoAdSQLUpdateTest : RepoNoteUpdateTest() {
    override val repo: INoteRepository = SqlTestCompanion.repoUnderTestContainer(
        initObjects,
        randomUuid = { lockNew.asString() },
    )
}
