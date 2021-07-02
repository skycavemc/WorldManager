import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.10"
    id("com.github.johnrengelman.shadow") version "4.0.4"
}

group = "de.leonheuer.worldmanager"
version = "1.0.3"

repositories {
    mavenCentral()
    maven { url = uri("https://papermc.io/repo/repository/maven-public/") }
}

dependencies {
    testImplementation(kotlin("test"))
    compileOnly("com.destroystokyo.paper:paper-api:1.16.5-R0.1-SNAPSHOT")
}

tasks {
    test {
        useJUnit()
    }

    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }

    named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
        archiveBaseName.set("${project.name}-standalone")
        mergeServiceFiles()
        manifest {
            attributes(mapOf("Main-Class" to "de.leonheuer.worldmanager.WorldManager"))
        }
        dependencies {
            exclude(dependency("com.destroystokyo.paper:paper-api:1.16.5-R0.1-SNAPSHOT"))
        }
    }

    build {
        dependsOn(shadowJar)
    }
}