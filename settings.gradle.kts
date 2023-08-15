rootProject.name = "tigernotes"

pluginManagement {
    val kotlinVersion: String by settings
    val openapiVersion: String by settings
    val ktorVersion: String by settings

    plugins {
        kotlin("jvm") version kotlinVersion apply false
        kotlin("plugin.serialization") version kotlinVersion apply false

        id("org.openapi.generator") version openapiVersion apply false
        id("io.ktor.plugin") version ktorVersion apply false
    }
}

// include("m1l1-hello")
// include("tn-acceptance")

include("tn-api-kmp")

include("tn-common")
include("tn-mappers")

include("tn-app-biz")
include("tn-stubs")

include("tn-app-ktor")
include("tn-app-kafka")

include("tn-lib-logging-common")
include("tn-lib-logging-kermit")
include("tn-repo-in-memory")
include("tn-repo-stubs")
include("tn-repo-tests")
include("tn-repo-postgresql")
include("tn-app-auth")
