package ru.otus.tigernotes.app

import com.auth0.jwt.JWT
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import ru.otus.tigernotes.api.apiMapper
import ru.otus.tigernotes.app.base.resolveAlgorithm
import ru.otus.tigernotes.app.config.KtorAuthConfig.Companion.GROUPS_CLAIM
import ru.otus.tigernotes.app.plugins.initAppSettings

fun Application.module(appSettings: TnAppSettings = initAppSettings()) {
    authentication {
        jwt("auth-jwt") {
            val authConfig = appSettings.auth
            realm = authConfig.realm

            verifier {
                val algorithm = it.resolveAlgorithm(authConfig)
                JWT
                    .require(algorithm)
                    .withAudience(authConfig.audience)
                    .withIssuer(authConfig.issuer)
                    .build()
            }
            validate { jwtCredential: JWTCredential ->
                when {
                    jwtCredential.payload.getClaim(GROUPS_CLAIM).asList(String::class.java).isNullOrEmpty() -> {
                        this@module.log.error("Groups claim must not be empty in JWT token")
                        null
                    }

                    else -> JWTPrincipal(jwtCredential.payload)
                }
            }
        }
    }
    routing {
        route("app") {
            install(ContentNegotiation) {
                json(apiMapper)
            }
            authenticate("auth-jwt") {
                note(appSettings)
            }
        }
    }
}

fun main() {
    embeddedServer(CIO, port = 8080, module = Application::module).start(wait = true)
}
