package ru.otus.tigernotes.app.config

import io.ktor.server.application.*

data class PgConfig(
    val url: String = "jdbc:postgresql://localhost:5432/tigernotes",
    val user: String = "postgres",
    val password: String = "password",
    val schema: String = "",
    val dropDatabase: Boolean = false
) {
    constructor(environment: ApplicationEnvironment): this(
        url = environment.config.property("repo.url").getString(),
        user = environment.config.property("repo.user").getString(),
        password = environment.config.property("repo.password").getString(),
        schema = environment.config.property("repo.schema").getString(),
        dropDatabase = environment.config.property("repo.dropDatabase").getString().toBoolean()
    )
}