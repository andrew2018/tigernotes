package ru.otus.tigernotes.repo.inmemory

import ru.otus.tigernotes.common.repo.INoteRepository
import ru.otus.tigernotes.repo.tests.RepoNoteDeleteTest

class NoteRepoInMemoryDeleteTest : RepoNoteDeleteTest() {
    override val repo: INoteRepository = NoteRepoInMemory(
        initObjects = initObjects
    )
}
