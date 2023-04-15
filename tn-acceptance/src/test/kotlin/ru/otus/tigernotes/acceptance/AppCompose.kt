package ru.otus.tigernotes.acceptance

import io.ktor.http.*
import org.testcontainers.containers.DockerComposeContainer
import java.io.File

@Suppress("unused")
class AppCompose private constructor() {
    private val _service = "app_1"
    private val _port = 80

    private val compose =
        DockerComposeContainer(File("../deploy/docker-compose-fbos.yml")).apply {
            withExposedService(
                _service,
                _port,
//                Wait.forListeningPort().withStartupTimeout(Duration.ofSeconds(30))
            )
            withLocalCompose(true)
            start()
        }

    val hostApp: String = compose.getServiceHost(_service, _port)
    val portApp: Int = compose.getServicePort(_service, _port)

    val url: Url = URLBuilder(
        protocol = URLProtocol.HTTP,
        host = hostApp,
        port = portApp,
    ).build()

    fun close() {
        compose.close()
    }

    companion object {
        val C by lazy { AppCompose() }
    }
}
