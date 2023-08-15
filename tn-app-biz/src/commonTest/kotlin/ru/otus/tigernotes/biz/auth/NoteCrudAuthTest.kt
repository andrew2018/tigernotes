package ru.otus.tigernotes.biz.auth

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import ru.otus.tigernotes.biz.NoteProcessor
import ru.otus.tigernotes.common.TnContext
import ru.otus.tigernotes.common.TnCorSettings
import ru.otus.tigernotes.common.models.*
import ru.otus.tigernotes.common.permissions.TnPrincipalModel
import ru.otus.tigernotes.common.permissions.TnUserGroups
import ru.otus.tigernotes.repo.inmemory.NoteRepoInMemory
import ru.otus.tigernotes.stubs.NoteStub
import kotlin.test.*

/**
 * @crud - экземпляр класса-фасада бизнес-логики
 * @context - контекст, смапленный из транспортной модели запроса
 */
@OptIn(ExperimentalCoroutinesApi::class)
class NoteCrudAuthTest {
    @Test
    fun createSuccessTest() = runTest {
        val userId = TnUserId("123")
        val repo = NoteRepoInMemory()
        val processor = NoteProcessor(
            settings = TnCorSettings(
                repoTest = repo
            )
        )
        val context = TnContext(
            workMode = TnWorkMode.TEST,
            note = NoteStub.prepareResult {
                permissionsClient.clear()
                id = NoteId.NONE
            },
            command = TnCommand.CREATE,
            principal = TnPrincipalModel(
                id = userId,
                groups = setOf(
                    TnUserGroups.USER,
                    TnUserGroups.TEST,
                )
            )
        )

        processor.exec(context)
        assertEquals(TnState.FINISHING, context.state)
        with(context.noteResponse) {
            assertTrue { id.asString().isNotBlank() }
            assertContains(permissionsClient, NotePermissionClient.READ)
            assertContains(permissionsClient, NotePermissionClient.UPDATE)
            assertContains(permissionsClient, NotePermissionClient.DELETE)
        }
    }

    @Test
    fun readSuccessTest() = runTest {
        val noteObj = NoteStub.get()
        val userId = noteObj.ownerId
        val noteId = noteObj.id
        val repo = NoteRepoInMemory(initObjects = listOf(noteObj))
        val processor = NoteProcessor(
            settings = TnCorSettings(
                repoTest = repo
            )
        )
        val context = TnContext(
            command = TnCommand.READ,
            workMode = TnWorkMode.TEST,
            note = Note(id = noteId),
            principal = TnPrincipalModel(
                id = userId,
                groups = setOf(
                    TnUserGroups.USER,
                    TnUserGroups.TEST,
                )
            )
        )
        processor.exec(context)
        assertEquals(TnState.FINISHING, context.state)
        with(context.noteResponse) {
            assertTrue { id.asString().isNotBlank() }
            assertContains(permissionsClient, NotePermissionClient.READ)
            assertContains(permissionsClient, NotePermissionClient.UPDATE)
            assertContains(permissionsClient, NotePermissionClient.DELETE)
        }
    }

}
