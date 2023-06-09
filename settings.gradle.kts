rootProject.name = "KrakenCore"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            library("annotations", "org.jetbrains:annotations:24.0.1")
            library("spigot-api", "org.spigotmc:spigot-api:1.18.2-R0.1-SNAPSHOT")
    
            version("jooq", "3.17.8")
            library("jooq", "org.jooq","jooq").versionRef("jooq")
            library("jooq-codegen", "org.jooq", "jooq-codegen").versionRef("jooq")
            library("jooq-meta", "org.jooq", "jooq-meta").versionRef("jooq")
            library("jooq-meta-extensions","org.jooq","jooq-meta-extensions").versionRef("jooq")
            
            version("configurate", "4.1.2")
            library("configurate-core", "org.spongepowered", "configurate-core").versionRef("configurate")
            library("configurate-yaml", "org.spongepowered", "configurate-yaml").versionRef("configurate")
            library("configurate-gson", "org.spongepowered", "configurate-gson").versionRef("configurate")
            
            version("adventure", "4.13.0")
            library("adventure-api", "net.kyori","adventure-api").versionRef("adventure")
            library("adventure-minimessage", "net.kyori", "adventure-text-minimessage").versionRef("adventure")
            library("adventure-bukkit","net.kyori:adventure-platform-bukkit:4.3.0")
            
            library("placeholder-api","me.clip:placeholderapi:2.11.2")
            
            library("artifact-version", "org.apache.maven:maven-artifact:3.9.0")
    
            library("mockito", "org.mockito:mockito-core:5.1.1")
            library("junit-platform","org.junit:junit-bom:5.9.2")
            library("junit-jupiter", "org.junit.jupiter","junit-jupiter").withoutVersion()
            
            library("lapzupi-connection", "com.github.Lapzupi:LapzupiConnection:1.0.2")
            library("lapzupi-config", "com.github.Lapzupi:LapzupiConfig:1.1.0")
            library("lapzupi-files", "com.github.Lapzupi:LapzupiFiles:1c7a837b53")
            
            library("slf4j", "org.slf4j:slf4j-api:2.0.5")
        }
    }
}