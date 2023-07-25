package ru.otus.tigernotes.common.exceptions

import ru.otus.tigernotes.common.models.NoteLock

class RepoConcurrencyException(expectedLock: NoteLock?, actualLock: NoteLock?): RuntimeException(
    "Expected lock is $expectedLock while actual lock in db is $actualLock"
)
