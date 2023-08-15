package ru.otus.tigernotes.repo.inmemory

import com.benasher44.uuid.uuid4
import io.github.reactivecircus.cache4k.Cache
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import ru.otus.tigernotes.common.NONE
import ru.otus.tigernotes.common.helpers.errorRepoConcurrency
import ru.otus.tigernotes.common.models.*
import ru.otus.tigernotes.common.repo.*
import ru.otus.tigernotes.repo.inmemory.model.NoteEntity
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

class NoteRepoInMemory(
    initObjects: List<Note> = emptyList(),
    ttl: Duration = 2.minutes,
    val randomUuid: () -> String = { uuid4().toString() },
) : INoteRepository {

    private val cache = Cache.Builder<String, NoteEntity>()
        .expireAfterWrite(ttl)
        .build()
    private val mutex: Mutex = Mutex()

    init {
        initObjects.forEach {
            save(it)
        }
    }

    private fun save(note: Note) {
        val entity = NoteEntity(note)
        if (entity.id == null) {
            return
        }
        cache.put(entity.id, entity)
    }

    override suspend fun createNote(rq: DbNoteRequest): DbNoteResponse {
        val key = randomUuid()
        val note = rq.note.copy(id = NoteId(key), lock = NoteLock(randomUuid()))
        val entity = NoteEntity(note)
        cache.put(key, entity)
        return DbNoteResponse(
            data = note,
            isSuccess = true,
        )
    }

    override suspend fun readNote(rq: DbNoteIdRequest): DbNoteResponse {
        val key = rq.id.takeIf { it != NoteId.NONE }?.asString() ?: return resultErrorEmptyId
        return cache.get(key)
            ?.let {
                DbNoteResponse(
                    data = it.toInternal(),
                    isSuccess = true,
                )
            } ?: resultErrorNotFound
    }

    override suspend fun updateNote(rq: DbNoteRequest): DbNoteResponse {
        val key = rq.note.id.takeIf { it != NoteId.NONE }?.asString() ?: return resultErrorEmptyId
        val oldLock = rq.note.lock.takeIf { it != NoteLock.NONE }?.asString() ?: return resultErrorEmptyLock
        val newAd = rq.note.copy(lock = NoteLock(randomUuid()))
        val entity = NoteEntity(newAd)
        return mutex.withLock {
            val oldNote = cache.get(key)
            when {
                oldNote == null -> resultErrorNotFound
                oldNote.lock != oldLock -> DbNoteResponse(
                    data = oldNote.toInternal(),
                    isSuccess = false,
                    errors = listOf(errorRepoConcurrency(NoteLock(oldLock), oldNote.lock?.let { NoteLock(it) }))
                )

                else -> {
                    cache.put(key, entity)
                    DbNoteResponse(
                        data = newAd,
                        isSuccess = true,
                    )
                }
            }
        }
    }

    override suspend fun deleteNote(rq: DbNoteIdRequest): DbNoteResponse {
        val key = rq.id.takeIf { it != NoteId.NONE }?.asString() ?: return resultErrorEmptyId
        val oldLock = rq.lock.takeIf { it != NoteLock.NONE }?.asString() ?: return resultErrorEmptyLock
        return mutex.withLock {
            val oldAd = cache.get(key)
            when {
                oldAd == null -> resultErrorNotFound
                oldAd.lock != oldLock -> DbNoteResponse(
                    data = oldAd.toInternal(),
                    isSuccess = false,
                    errors = listOf(errorRepoConcurrency(NoteLock(oldLock), oldAd.lock?.let { NoteLock(it) }))
                )

                else -> {
                    cache.invalidate(key)
                    DbNoteResponse(
                        data = oldAd.toInternal(),
                        isSuccess = true,
                    )
                }
            }
        }
    }

    override suspend fun searchNote(rq: DbNoteFilterRequest): DbNotesResponse {
        val result = cache.asMap().asSequence()
            .filter { entry ->
                rq.ownerId.takeIf { it != TnUserId.NONE }?.let {
                    it.asString() == entry.value.ownerId
                } ?: true
            }
            .filter { entry ->
                rq.searchTitle.takeIf { it.isNotBlank() }?.let {
                    entry.value.title?.contains(it) ?: false
                } ?: true
            }
            .filter { entry ->
                rq.dateStart.takeIf { it != LocalDate.NONE }?.let {
                    entry.value.timeCreate?.toLocalDateTime(TimeZone.UTC)?.date?.compareTo(it)!! >= 0
                } ?: true
            }
            .filter { entry ->
                rq.dateEnd.takeIf { it != LocalDate.NONE }?.let {
                    entry.value.timeCreate?.toLocalDateTime(TimeZone.UTC)?.date?.compareTo(it)!! <= 0
                } ?: true
            }

            .map { it.value.toInternal() }
            .toList()
        return DbNotesResponse(
            data = result,
            isSuccess = true
        )
    }

    companion object {
        val resultErrorEmptyId = DbNoteResponse(
            data = null,
            isSuccess = false,
            errors = listOf(
                TnError(
                    code = "id-empty",
                    group = "validation",
                    field = "id",
                    message = "Id must not be null or blank"
                )
            )
        )
        val resultErrorEmptyLock = DbNoteResponse(
            data = null,
            isSuccess = false,
            errors = listOf(
                TnError(
                    code = "lock-empty",
                    group = "validation",
                    field = "lock",
                    message = "Lock must not be null or blank"
                )
            )
        )
        val resultErrorNotFound = DbNoteResponse(
            isSuccess = false,
            data = null,
            errors = listOf(
                TnError(
                    code = "not-found",
                    field = "id",
                    message = "Not Found"
                )
            )
        )
    }
}
