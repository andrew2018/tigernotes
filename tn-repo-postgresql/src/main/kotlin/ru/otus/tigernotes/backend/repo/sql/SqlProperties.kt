package ru.otus.tigernotes.backend.repo.sql

open class SqlProperties(
    val url: String = "jdbc:postgresql://localhost:5432/tigernotes",
    val user: String = "postgres",
    val password: String = "password",
    val schema: String = "tigernotes",
    // Delete tables at startup - needed for testing
    val dropDatabase: Boolean = false,
)
