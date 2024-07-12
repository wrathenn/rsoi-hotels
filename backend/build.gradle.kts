import org.springframework.boot.gradle.tasks.bundling.BootJar

val springVersion: String by project
val springCloudVersion: String by project

plugins {
    // version sharing
    kotlin("jvm") version "1.9.10" apply false
    kotlin("plugin.spring") version "1.9.10" apply false
    id("org.springframework.boot") version "3.2.1" apply false
    id("com.github.johnrengelman.shadow") version "7.1.2" apply false
    id("com.intershop.gradle.jaxb") version "5.2.1" apply false
    id("io.spring.dependency-management") version "1.1.0"
}

allprojects {
    apply(plugin = "com.github.johnrengelman.shadow")
    apply(plugin = "io.spring.dependency-management")

    dependencyManagement {
        imports {
            mavenBom("org.springframework.boot:spring-boot-dependencies:$springVersion")
            mavenBom("org.springframework.cloud:spring-cloud-dependencies:$springCloudVersion")
        }
    }

    repositories {
        mavenCentral()
    }

    tasks.withType<AbstractArchiveTask> {
        isPreserveFileTimestamps = false
        isReproducibleFileOrder = true
    }

    tasks.withType<BootJar> {
        archiveClassifier.set("all")
    }
}
