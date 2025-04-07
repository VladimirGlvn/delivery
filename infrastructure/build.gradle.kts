import com.google.protobuf.gradle.id

plugins {
    kotlin("jvm")
    kotlin("plugin.spring") version "2.1.0"
    id("com.google.protobuf") version "0.9.5"
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
    implementation("io.grpc:grpc-kotlin-stub:1.4.1")
    implementation("io.grpc:grpc-protobuf:1.71.0")
    implementation("com.google.protobuf:protobuf-kotlin:4.30.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.1")
    implementation("org.springframework.kafka:spring-kafka:3.3.4")

    compileOnly("com.fasterxml.jackson.core:jackson-databind:2.18.3")

    protobuf(files("contracts"))

    runtimeOnly("org.postgresql:postgresql:42.7.5")
    runtimeOnly("io.grpc:grpc-okhttp:1.71.0")

    testImplementation(kotlin("test"))
    testImplementation("org.springframework.boot:spring-boot-starter-test:3.4.4")
    testImplementation("org.springframework.boot:spring-boot-testcontainers:3.4.4")
    testImplementation("org.testcontainers:junit-jupiter:1.20.6")
    testImplementation("org.testcontainers:postgresql:1.20.6")
    testImplementation("io.mockk:mockk:1.13.17")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:4.30.2"
    }
    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.71.0"
        }
    }
    generateProtoTasks {
        ofSourceSet("main").forEach {
            it.plugins {
                id("grpc") { }
            }
        }
    }
}