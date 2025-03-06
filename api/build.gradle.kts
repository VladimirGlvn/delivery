plugins {
    kotlin("jvm") version "2.1.0"
}

group = "me.vgolovnin.ddd.delivery"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":core"))
    implementation(project(":infrastructure"))
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}