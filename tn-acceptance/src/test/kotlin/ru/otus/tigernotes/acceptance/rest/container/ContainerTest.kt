package ru.otus.tigernotes.acceptance.rest.container

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.core.test.TestCaseSeverityLevel
import io.kotest.matchers.shouldBe
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.request.*
import ru.otus.tigernotes.acceptance.AppCompose

class ContainerTest : BehaviorSpec({
    severity = TestCaseSeverityLevel.NORMAL

    val client = HttpClient(OkHttp)
    given("I am going to send request") {
        `when`("I send request") {
            val result = client
                .get {
                    url(AppCompose.C.url)
                }
                .call
            val resp = result.response

            then("Response status is 200") {
                resp.status.value shouldBe 200
            }
        }

    }
})
