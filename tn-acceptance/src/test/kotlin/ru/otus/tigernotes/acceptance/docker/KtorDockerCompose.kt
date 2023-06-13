package ru.otus.tigernotes.acceptance.docker

import ru.otus.tigernotes.acceptance.fixture.docker.AbstractDockerCompose

object KtorDockerCompose : AbstractDockerCompose(
    "app-ktor_1", 8080, "ktor/docker-compose-ktor.yml"
)