package ru.otus.tigernotes.repo.inmemory

import ru.otus.tigernotes.common.repo.INoteRepository
import ru.otus.tigernotes.repo.tests.RepoNoteUpdateTest

class NoteRepoInMemoryUpdateTest : RepoNoteUpdateTest() {
    override val repo: INoteRepository = NoteRepoInMemory(
        initObjects = initObjects,
        randomUuid = { lockNew.asString() }
    )
}
