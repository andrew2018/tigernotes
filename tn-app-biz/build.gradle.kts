plugins {
    kotlin("multiplatform")
}

kotlin {
    jvm {}
    linuxX64 {}
    macosX64 {}

    sourceSets {
        val crowdprojCorVersion: String by project
        val coroutinesVersion: String by project

        @Suppress("UNUSED_VARIABLE")
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation("com.crowdproj:kotlin-cor:$crowdprojCorVersion")

                implementation(project(":tn-common"))
                implementation(project(":tn-app-auth"))
                implementation(project(":tn-repo-in-memory"))
                implementation(project(":tn-stubs"))
            }
        }
        @Suppress("UNUSED_VARIABLE")
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))

                implementation(project(":tn-repo-tests"))
                implementation(project(":tn-repo-stubs"))
                api("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")
            }
        }
    }
}
