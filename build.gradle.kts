val ktorVersion: String by project
val kotlinVersion: String by project
val logbackVersion: String by project
val exposedVersion: String by project
val daggerVersion: String by project
val koinVersion: String by project
val psqlVersion: String by project
val valiktorVersion: String by project

plugins {
    application
    kotlin("jvm") version "1.5.10"
    kotlin("kapt") version "1.5.10"
    id ("com.github.johnrengelman.shadow") version "7.0.0"
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
    }
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    archiveFileName.set("analythicc-uber.jar")
}

group = "cc.analythi"
version = "0.0.1"
application {
    mainClass.set("cc.analythi.ApplicationKt")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-locations:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("io.ktor:ktor-gson:$ktorVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    testImplementation("io.ktor:ktor-server-tests:$ktorVersion")

    // exposed ORM
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-java-time:$exposedVersion")

    // postgres jdbc driver
    implementation("org.postgresql:postgresql:$psqlVersion")

    // Koin for Ktor
    implementation ("io.insert-koin:koin-ktor:$koinVersion")
    // SLF4J Logger
    implementation ("io.insert-koin:koin-logger-slf4j:$koinVersion")

    // hibernate validator
    implementation("org.valiktor:valiktor-core:$valiktorVersion")
}