rootProject.name = "tigernotes"

pluginManagement {
    val kotlinVersion: String by settings
    val openapiVersion: String by settings

    plugins {
        kotlin("jvm") version kotlinVersion apply false
        kotlin("plugin.serialization") version kotlinVersion apply false

        id("org.openapi.generator") version openapiVersion apply false
    }
}

// include("m1l1-hello")
include("tn-acceptance")

include("tn-api-v2-kmp")

include("tn-common")
include("tn-mappers")
