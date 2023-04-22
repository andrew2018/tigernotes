package ru.otus.tigernotes.acceptance

import io.kotest.core.annotation.AutoScan
import io.kotest.core.listeners.AfterProjectListener
import io.kotest.core.listeners.BeforeProjectListener

@Suppress("unused")
@AutoScan
object ProjectListener : BeforeProjectListener, AfterProjectListener {
    override suspend fun beforeProject() {
        println("Project starting")
        val host = AppCompose.C.hostApp
        val port = AppCompose.C.portApp
        println("Started docker-compose with App at HOST: $host PORT: $port")
    }
    override suspend fun afterProject() {
        AppCompose.C.close()
        println("Project complete")
    }
}
