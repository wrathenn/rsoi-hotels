import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val springVersion: String by project
val jdbiVersion: String by project
val postgresVersion: String by project
val mockitoVersion: String by project

plugins {
    application
    kotlin("jvm")
    kotlin("plugin.spring")
    id("org.springframework.boot")
}

group = "com.wrathenn"
version = "1.0-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

dependencies {
    implementation(project(":util"))

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.1.0")
    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
    // adds JwtDecoder bean in context
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")

    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
    implementation("org.springframework.cloud:spring-cloud-circuitbreaker-resilience4j:3.1.0")

    implementation("org.springframework.kafka:spring-kafka")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.mockito:mockito-core")
    testImplementation("org.mockito.kotlin:mockito-kotlin:$mockitoVersion")
}

application {
    mainClass.set("com.wrathenn.gateway.service.GatewayServiceApplicationKt")
}

configurations.all { exclude(group = "org.eclipse.angus", module = "angus-activation") }

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict", "-Xcontext-receivers")
        jvmTarget = "17"
        languageVersion = "1.9"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
