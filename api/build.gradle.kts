plugins {
    kotlin("jvm")
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