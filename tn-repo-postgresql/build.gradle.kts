plugins {
    kotlin("jvm")
}

dependencies {
    val exposedVersion: String by project
    val postgresDriverVersion: String by project
    val kmpUUIDVersion: String by project
    val testcontainersVersion: String by project
    val logbackVersion: String by project

    implementation(kotlin("stdlib"))

    implementation(project(":tn-common"))

    implementation("org.postgresql:postgresql:$postgresDriverVersion")

    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-java-time:$exposedVersion")
    implementation("com.benasher44:uuid:$kmpUUIDVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")


    testImplementation("org.testcontainers:postgresql:$testcontainersVersion")
    testImplementation(project(":tn-repo-tests"))
}
