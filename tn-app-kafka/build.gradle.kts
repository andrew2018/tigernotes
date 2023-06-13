plugins {
    application
    kotlin("jvm")
}

application {
    mainClass.set("ru.otus.tigernotes.app.kafka.MainKt")
}

dependencies {
    val kafkaVersion: String by project
    val coroutinesVersion: String by project
    val atomicfuVersion: String by project
    val logbackVersion: String by project
    val kotlinLoggingJvmVersion: String by project
    implementation("org.apache.kafka:kafka-clients:$kafkaVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:atomicfu:$atomicfuVersion")

    // log
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("io.github.microutils:kotlin-logging-jvm:$kotlinLoggingJvmVersion")

    // transport models
    implementation(project(":tn-common"))
    implementation(project(":tn-api-kmp"))
    implementation(project(":tn-mappers"))
    // logic
    implementation(project(":tn-app-biz"))

    testImplementation(kotlin("test-junit"))
}
