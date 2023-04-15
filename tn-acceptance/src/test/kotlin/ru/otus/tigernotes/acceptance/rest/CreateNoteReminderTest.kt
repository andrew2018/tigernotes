package ru.otus.tigernotes.acceptance.rest

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.core.test.TestCaseSeverityLevel

class CreateNoteReminderTest : BehaviorSpec({
    severity = TestCaseSeverityLevel.MINOR

    given("I am going to create note") {
        `when`("I filled heading, time reminder and email") {
            // add test
            then("Response status is 200") {
                // add result
            }
            then("Note saved") {
                // add result
            }
        }
        `when`("I didn't fill heading, fill time reminder and email") {
            // add test
            then("Error validation: heading is null") {
                // add result
            }
        }
        `when`("I filled very big heading, fill time reminder and email") {
            // add test
            then("Error validation: heading is big") {
                // add result
            }
        }
        `when`("I filled heading, email, but didn't fill time reminder") {
            // add test
            then("Error validation: time reminder is null") {
                // add result
            }
        }
        `when`("I filled heading, email, but time reminder is incorrect") {
            // add test
            then("Error validation: time reminder is incorrect") {
                // add result
            }
        }
        `when`("I filled heading, time, but didn't fill email") {
            // add test
            then("Error validation: email is null") {
                // add result
            }
        }
        `when`("I filled heading, time, but email isn't correct") {
            // add test
            then("Error validation: email is incorrect") {
                // add result
            }
        }
    }
})
