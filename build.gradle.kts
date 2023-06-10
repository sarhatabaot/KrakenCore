import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    
    id("maven-publish")
    kotlin("jvm") version "1.8.20"

    jacoco
}

group = "com.github.sarhatabaot"
version = "1.8.4-SNAPSHOT"

repositories {
    maven(
        url = "https://hub.spigotmc.org/nexus/content/repositories/snapshots/"
    )
    maven(
        url = "https://repo.aikar.co/content/groups/aikar/"
    )
    maven(
        url = "https://mvn.lumine.io/repository/maven-snapshots/"
    )
    maven(
        url = "https://repo.extendedclip.com/content/repositories/placeholderapi/"
    )
    maven (
        url ="https://jitpack.io"
    )
}

dependencies {
    compileOnly(libs.spigot.api)
    compileOnly(libs.placeholder.api)
    
    compileOnly(libs.annotations)
    compileOnly(libs.adventure.api)
    compileOnly(libs.adventure.minimessage)
    compileOnly(libs.adventure.bukkit)
    
    compileOnly(libs.artifact.version)
    compileOnly(libs.jooq)
    
    compileOnly(libs.slf4j)
    
    api(libs.lapzupi.config)
    api(libs.lapzupi.connection)
    api(libs.lapzupi.files)
    
    testImplementation(libs.mockito)
    testImplementation(platform(libs.junit.platform))
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.jooq)
    testImplementation(libs.slf4j)
    testImplementation(libs.spigot.api)
    testImplementation(libs.artifact.version)
}

jacoco {
    toolVersion = "0.8.8"
}

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport) // report is always generated after tests run
}
tasks.jacocoTestReport {
    dependsOn(tasks.test) // tests are required to run before generating the report
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}

java {
    withSourcesJar()

    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

publishing {
    publications {
        create<MavenPublication>("KrakenCore") {
            groupId = groupId
            artifactId = artifactId
            version = version

            from(components.getByName("java"))
        }
    }
}