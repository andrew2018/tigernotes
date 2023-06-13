plugins {
    kotlin("jvm")
}

dependencies {
    val kotestVersion: String by project
    val testcontainersVersion: String by project
    val ktorVersion: String by project
    val kotlinLoggingJvmVersion: String by project
    val logbackVersion: String by project

    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.kotest:kotest-framework-datatest:$kotestVersion")
    testImplementation("io.kotest:kotest-property:$kotestVersion")
    testImplementation("io.github.microutils:kotlin-logging-jvm:$kotlinLoggingJvmVersion")
    testImplementation("ch.qos.logback:logback-classic:$logbackVersion")

    testImplementation(project(":tn-api-kmp"))

    testImplementation("org.testcontainers:testcontainers:$testcontainersVersion")

    testImplementation("io.ktor:ktor-client-core:$ktorVersion")
    testImplementation("io.ktor:ktor-client-okhttp:$ktorVersion")
}

var severity: String = "MINOR"

tasks {
    withType<Test>().configureEach {
        useJUnitPlatform()
        dependsOn(":tn-app-ktor:publishImageToLocalRegistry")
    }
    test {
        systemProperty("kotest.framework.test.severity", "NORMAL")
    }
    create<Test>("test-strict") {
        systemProperty("kotest.framework.test.severity", "MINOR")
        group = "verification"
    }
}