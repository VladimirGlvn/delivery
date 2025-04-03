plugins {
    kotlin("jvm")
    kotlin("plugin.spring") version "2.1.0"
    id("org.springframework.boot") version "3.4.4"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.openapi.generator") version "7.12.0"
}

group = "me.vgolovnin.ddd.delivery"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":core"))
    implementation(project(":infrastructure"))
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.1")
    implementation("dev.ceviz:spring-3x-kordinator:1.0.0")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0")

    developmentOnly("org.springframework.boot:spring-boot-devtools")

    testImplementation(kotlin("test"))
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}

openApiGenerate {
    inputSpec = "openapi.yml"
    generatorName = "kotlin-spring"
    outputDir = "$projectDir"
    generateApiTests = false
    generateModelTests = false
    generateApiDocumentation = false
    generateModelDocumentation = false
    supportingFilesConstrainedTo = listOf()
    modelPackage = "me.vgolovnin.ddd.delivery.api.adapters.http"
    apiPackage = "me.vgolovnin.ddd.delivery.api.adapters.http"
    configOptions = mapOf(
        "basePackage" to "me.vgolovnin.ddd.delivery.api.config",
        "interfaceOnly" to "true",
        "exceptionHandler" to "false",
        "gradleBuildFile" to "false",
        "skipDefaultInterface" to "true",
        "useSpringBoot3" to "true",
        "useTags" to "true",
    )
}