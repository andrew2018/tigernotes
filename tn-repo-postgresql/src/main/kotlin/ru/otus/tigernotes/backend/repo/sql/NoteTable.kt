package ru.otus.tigernotes.backend.repo.sql

import kotlinx.datetime.toJavaInstant
import kotlinx.datetime.toKotlinInstant
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.timestamp
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import ru.otus.tigernotes.common.models.Note
import ru.otus.tigernotes.common.models.NoteId
import ru.otus.tigernotes.common.models.NoteLock
import java.time.Instant

object NoteTable : Table("note") {
    val id = varchar("id", 128)
    val title = varchar("title", 128)
    val description = varchar("description", 512)
    val timeCreate = timestamp("timeCreate")
    val email = varchar("email", 128)
    val timeReminder = timestamp("timeReminder")
    val lock = varchar("lock", 50)

    override val primaryKey = PrimaryKey(id)

    fun from(res: InsertStatement<Number>) = Note(
        id = NoteId(res[id].toString()),
        title = res[title],
        description = res[description],
        timeCreate = res[timeCreate].toKotlinInstant(),
        email = res[email],
        timeReminder = res[timeReminder].toKotlinInstant(),
        lock = NoteLock(res[lock])
    )

    fun from(res: ResultRow) = Note(
        id = NoteId(res[id].toString()),
        title = res[title],
        description = res[description],
        timeCreate = res[timeCreate].toKotlinInstant(),
        email = res[email],
        timeReminder = res[timeReminder].toKotlinInstant(),
        lock = NoteLock(res[lock])
    )

    fun to(it: UpdateBuilder<*>, note: Note, randomUuid: () -> String) {
        it[id] = note.id.takeIf { it != NoteId.NONE }?.asString() ?: randomUuid()
        it[title] = note.title
        it[description] = note.description
        it[timeCreate] = Instant.now()
        it[email] = note.email
        it[timeReminder] = note.timeReminder.toJavaInstant()
        it[lock] = note.lock.takeIf { it != NoteLock.NONE }?.asString() ?: randomUuid()
    }

}
