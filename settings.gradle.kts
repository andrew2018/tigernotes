rootProject.name = "tigernotes"
// include("m1l1-hello")
include("tn-acceptance")

pluginManagement {
    val kotlinVersion: String by settings
    plugins {
        kotlin("jvm") version kotlinVersion apply false
    }
}