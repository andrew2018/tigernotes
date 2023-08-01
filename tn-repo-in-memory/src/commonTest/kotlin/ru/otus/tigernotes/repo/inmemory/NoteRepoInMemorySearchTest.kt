package ru.otus.tigernotes.repo.inmemory

import ru.otus.tigernotes.common.repo.INoteRepository
import ru.otus.tigernotes.repo.tests.RepoNoteSearchTest

class NoteRepoInMemorySearchTest : RepoNoteSearchTest() {
    override val repo: INoteRepository = NoteRepoInMemory(
        initObjects = initObjects
    )
}
