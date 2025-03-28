plugins {
    kotlin("jvm")
    kotlin("plugin.spring") version "2.1.0"
}

group = "me.vgolovnin.ddd.delivery"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":core"))
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc:3.4.4")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.liquibase:liquibase-core:4.31.1")
    runtimeOnly("org.postgresql:postgresql:42.7.5")

    testImplementation(kotlin("test"))
    testImplementation("org.springframework.boot:spring-boot-starter-test:3.4.4")
    testImplementation("org.springframework.boot:spring-boot-testcontainers:3.4.4")
    testImplementation("org.testcontainers:junit-jupiter:1.20.6")
    testImplementation("org.testcontainers:postgresql:1.20.6")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}