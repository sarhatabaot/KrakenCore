plugins {
    id("java")
    id("maven-publish")
    id("com.github.johnrengelman.shadow") version "8.1.0"
    
    jacoco
}

version = "1.7.0"

repositories {
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
    
//    compileOnly(libs.hikaricp)
//    compileOnly(libs.flyway)
    compileOnly(libs.annotations)
    compileOnly(libs.adventure.api)
    compileOnly(libs.adventure.minimessage)
    compileOnly(libs.adventure.bukkit)
    
    compileOnly(libs.artifact.version)
    compileOnly(libs.jooq)
    
    compileOnly(libs.slf4j)
    
    implementation(libs.lapzupi.config)
    implementation(libs.lapzupi.connection)
    
    testImplementation(libs.mockito)
    testImplementation(platform(libs.junit.platform))
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.jooq)
    testImplementation(libs.slf4j)
    testImplementation(libs.spigot.api)
    testImplementation(libs.artifact.version)
}

tasks {
    build {
        dependsOn(shadowJar)
    }
    
    shadowJar {
        minimize()
        
        relocate("com.lapzupi.dev.connection", "com.github.sarhatabaot.kraken.core.db")
        relocate("com.lapzupi.dev.config", "com.github.sarhatabaot.kraken.core.config")
    }
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
