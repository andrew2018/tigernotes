plugins {
    kotlin("jvm")
}

dependencies {
    val kotestVersion: String by project
    val testcontainersVersion: String by project
    val ktorVersion: String by project

    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.kotest:kotest-framework-datatest:$kotestVersion")
    testImplementation("io.kotest:kotest-property:$kotestVersion")

    testImplementation("org.testcontainers:testcontainers:$testcontainersVersion")

    testImplementation("io.ktor:ktor-client-core:$ktorVersion")
    testImplementation("io.ktor:ktor-client-okhttp:$ktorVersion")
}

var severity: String = "MINOR"

tasks {
    withType<Test>().configureEach {
        useJUnitPlatform()
    }
    test {
        systemProperty("kotest.framework.test.severity", "NORMAL")
    }
    create<Test>("test-strict") {
        systemProperty("kotest.framework.test.severity", "MINOR")
        group = "verification"
    }
}