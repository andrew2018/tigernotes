package ru.otus.tigernotes.acceptance.rest

import io.kotest.core.annotation.Ignored
import ru.otus.tigernotes.acceptance.docker.KtorDockerCompose
import ru.otus.tigernotes.acceptance.fixture.BaseFunSpec
import ru.otus.tigernotes.acceptance.fixture.client.RestClient
import ru.otus.tigernotes.acceptance.fixture.docker.DockerCompose

@Ignored
open class AccRestTestBase(dockerCompose: DockerCompose) : BaseFunSpec(dockerCompose, {
    val restClient = RestClient(dockerCompose)
    testApiNote(restClient, "rest ")
    testStubApiNote(restClient, "rest ")
})

class AccRestKtorTest : AccRestTestBase(KtorDockerCompose)
