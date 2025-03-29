plugins {
    kotlin("jvm")
}

group = "me.vgolovnin.ddd.delivery"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    api("io.arrow-kt:arrow-core:2.0.1")

    testImplementation(kotlin("test"))
    testImplementation("io.kotest.extensions:kotest-assertions-arrow:2.0.0")
    testImplementation("io.kotest:kotest-assertions-core-jvm:5.9.1")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.11.4")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}