package ru.otus.tigernotes.repo.inmemory

import ru.otus.tigernotes.repo.tests.RepoNoteCreateTest

class NoteRepoInMemoryCreateTest : RepoNoteCreateTest() {
    override val repo = NoteRepoInMemory(
        initObjects = initObjects,
        randomUuid = { lockNew.asString() }
    )
}