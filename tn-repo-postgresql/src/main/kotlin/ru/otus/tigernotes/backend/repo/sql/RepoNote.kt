package ru.otus.tigernotes.backend.repo.sql

import com.benasher44.uuid.uuid4
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toJavaLocalDate
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import ru.otus.tigernotes.common.NONE
import ru.otus.tigernotes.common.helpers.asTnError
import ru.otus.tigernotes.common.models.Note
import ru.otus.tigernotes.common.models.NoteId
import ru.otus.tigernotes.common.models.NoteLock
import ru.otus.tigernotes.common.models.TnUserId
import ru.otus.tigernotes.common.repo.*
import java.time.ZoneOffset

class RepoNote(
    properties: SqlProperties,
    initObjects: Collection<Note> = emptyList(),
    val randomUuid: () -> String = { uuid4().toString() }
) : INoteRepository {

    init {
        val driver = when {
            properties.url.startsWith("jdbc:postgresql://") -> "org.postgresql.Driver"
            else -> throw IllegalArgumentException("Unknown driver for url ${properties.url}")
        }

        Database.connect(
            properties.url, driver, properties.user, properties.password
        )

        transaction {
            if (properties.dropDatabase) SchemaUtils.drop(NoteTable)
            SchemaUtils.create(NoteTable)
            initObjects.forEach { createNote(it) }
        }
    }

    private fun createNote(note: Note): Note {
        val res = NoteTable.insert {
            to(it, note, randomUuid)
        }

        return NoteTable.from(res)
    }

    private fun <T> transactionWrapper(block: () -> T, handle: (Exception) -> T): T =
        try {
            transaction {
                block()
            }
        } catch (e: Exception) {
            handle(e)
        }

    private fun transactionWrapper(block: () -> DbNoteResponse): DbNoteResponse =
        transactionWrapper(block) { DbNoteResponse.error(it.asTnError()) }

    override suspend fun createNote(rq: DbNoteRequest): DbNoteResponse = transactionWrapper {
        DbNoteResponse.success(createNote(rq.note))
    }

    private fun read(id: NoteId): DbNoteResponse {
        val res = NoteTable.select {
            NoteTable.id eq id.asString()
        }.singleOrNull() ?: return DbNoteResponse.errorNotFound
        return DbNoteResponse.success(NoteTable.from(res))
    }

    override suspend fun readNote(rq: DbNoteIdRequest): DbNoteResponse = transactionWrapper { read(rq.id) }

    private fun update(
        id: NoteId,
        lock: NoteLock,
        block: (Note) -> DbNoteResponse
    ): DbNoteResponse =
        transactionWrapper {
            if (id == NoteId.NONE) return@transactionWrapper DbNoteResponse.errorEmptyId

            val current = NoteTable.select { NoteTable.id eq id.asString() }
                .firstOrNull()
                ?.let { NoteTable.from(it) }

            when {
                current == null -> DbNoteResponse.errorNotFound
                current.lock != lock -> DbNoteResponse.errorConcurrent(lock, current)
                else -> block(current)
            }
        }

    override suspend fun updateNote(rq: DbNoteRequest): DbNoteResponse =
        update(rq.note.id, rq.note.lock) {
            NoteTable.update({ NoteTable.id eq rq.note.id.asString() }) {
                to(it, rq.note.copy(lock = NoteLock(randomUuid())), randomUuid)
            }
            read(rq.note.id)
        }

    override suspend fun deleteNote(rq: DbNoteIdRequest): DbNoteResponse = update(rq.id, rq.lock) {
        NoteTable.deleteWhere { NoteTable.id eq rq.id.asString() }
        DbNoteResponse.success(it)
    }

    override suspend fun searchNote(rq: DbNoteFilterRequest): DbNotesResponse =
        transactionWrapper({
            val res = NoteTable.select {
                buildList {
                    add(Op.TRUE)
                    if (rq.ownerId != TnUserId.NONE) {
                        add(NoteTable.owner eq rq.ownerId.asString())
                    }
                    if (rq.searchTitle.isNotBlank()) {
                        add(
                            (NoteTable.title like "%${rq.searchTitle}%")
                                    or (NoteTable.description like "%${rq.searchTitle}%")
                        )
                    }
                    if (rq.dateStart != LocalDate.NONE ) {
                        add(NoteTable.timeCreate greaterEq rq.dateStart.toJavaLocalDate().atStartOfDay().toInstant(
                            ZoneOffset.UTC))
                    }
                    if (rq.dateEnd != LocalDate.NONE ) {
                        add(NoteTable.timeCreate lessEq rq.dateEnd.toJavaLocalDate().atStartOfDay().toInstant(
                            ZoneOffset.UTC))
                    }
                }.reduce { a, b -> a and b }
            }
            DbNotesResponse(data = res.map { NoteTable.from(it) }, isSuccess = true)
        }, {
            DbNotesResponse.error(it.asTnError())
        })
}
