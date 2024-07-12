import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val jdbiVersion: String by project
val postgresVersion: String by project
val mockitoVersion: String by project

plugins {
    application
    kotlin("jvm")
    kotlin("plugin.spring")
    id("org.springframework.boot")
}

group = "com.wrathenn.payment"
version = "1.0-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

application {
    mainClass.set("com.wrathenn.payment.service.PaymentServiceApplicationKt")
}

dependencies {
    implementation(project(":util"))

    implementation("org.jdbi:jdbi3-core:$jdbiVersion")
    implementation("org.jdbi:jdbi3-kotlin:$jdbiVersion")
    implementation("org.jdbi:jdbi3-json:$jdbiVersion")
    implementation("org.jdbi:jdbi3-postgres:$jdbiVersion")
    implementation("org.jdbi:jdbi3-postgis:$jdbiVersion")
    implementation("org.jdbi:jdbi3-jackson2:$jdbiVersion")

    implementation("org.postgresql:postgresql:$postgresVersion")

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.1.0")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.mockito:mockito-core")
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
