package ru.otus.tigernotes.acceptance.rest

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.core.test.TestCaseSeverityLevel

class CreateNoteTest : BehaviorSpec({
    severity = TestCaseSeverityLevel.MINOR

    given("I am going to create note") {
        `when`("I filled heading") {
            // add test
            then("Response status is 200") {
                // add result
            }
            then("Note saved") {
                // add result
            }
        }
        `when`("I didn't fill heading") {
            // add test
            then("Error validation: heading is null") {
                // add result
            }
        }
        `when`("I filled very big heading") {
            // add test
            then("Error validation: heading is big") {
                // add result
            }
        }
    }
})
