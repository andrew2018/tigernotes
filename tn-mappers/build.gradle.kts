plugins {
    kotlin("multiplatform")
}

kotlin {
    jvm {}
    macosX64 {}
    linuxX64 {}

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))

                implementation(project(":tn-api-kmp"))
                implementation(project(":tn-common"))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
                implementation(kotlin("test-junit"))
            }
        }

    }
}
