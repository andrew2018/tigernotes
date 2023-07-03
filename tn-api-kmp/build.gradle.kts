import org.openapitools.generator.gradle.plugin.tasks.GenerateTask

plugins {
    kotlin("multiplatform")
    id("org.openapi.generator")
    kotlin("plugin.serialization")
}

kotlin {
    jvm { }
    linuxX64 { }
    macosX64 { }

    sourceSets {
        val serializationVersion: String by project

        @Suppress("UNUSED_VARIABLE")
        val commonMain by getting {

            kotlin.srcDirs("$buildDir/generate-resources/main/src/commonMain/kotlin")

            dependencies {
                implementation(kotlin("stdlib-common"))

                implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:$serializationVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")
            }
        }
        @Suppress("UNUSED_VARIABLE")
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
                implementation(kotlin("test-junit"))
            }
        }
    }
}

/**
 * Настраиваем генерацию здесь
 */
mapOf(
    "$rootDir/specs/specs-note.yaml" to "${rootProject.group}.api",
    "$rootDir/specs/specs-note-log.yaml" to "${rootProject.group}.log"
).forEach { (input, pkg) ->
    val genDir = pkg.substringAfterLast('.')
    tasks.register<GenerateTask>("openApiGenerate_$genDir") {
        generatorName.set("kotlin") // Это и есть активный генератор
        packageName.set(pkg)
        apiPackage.set(pkg)
        modelPackage.set("$pkg.models")
        invokerPackage.set("$pkg.invoker")
        inputSpec.set(input)
        library.set("multiplatform")
        outputDir.set("$buildDir/generate-resources/main")

        /**
         * Здесь указываем, что нам нужны только модели, все остальное не нужно
         */
        globalProperties.apply {
            put("models", "")
            put("modelDocs", "false")
        }

        /**
         * Настройка дополнительных параметров из документации по генератору
         * https://github.com/OpenAPITools/openapi-generator/blob/master/docs/generators/kotlin.md
         */
        configOptions.set(
            mapOf(
                "dateLibrary" to "string",
                "enumPropertyNaming" to "UPPERCASE",
                "collectionType" to "list",
            )
        )
    }
}

afterEvaluate {
    val openApiGenerateOne = tasks.getByName("openApiGenerate_api")
    val openApiGenerateTwo = tasks.getByName("openApiGenerate_log")
    tasks.filter { it.name.startsWith("compile") }.forEach {
        it.dependsOn(openApiGenerateOne)
        it.dependsOn(openApiGenerateTwo)
    }
}