package ru.otus.tigernotes.app.auth

import io.ktor.client.request.*
import io.ktor.server.testing.*
import org.junit.Test
import ru.otus.tigernotes.app.config.KtorAuthConfig
import ru.otus.tigernotes.app.helpers.testSettings
import ru.otus.tigernotes.app.module
import kotlin.test.assertEquals

class AuthTest {
    @Test
    fun invalidAudience() = testApplication {
        val response = client.post("/app/note/create") {
            addAuth(config = KtorAuthConfig.TEST.copy(audience = "invalid"))
        }
        assertEquals(401, response.status.value)
    }
}
