rootProject.name = "rsoi"

pluginManagement {
    plugins {
        id("org.jetbrains.kotlin.jvm") version "1.9.10"
    }
}

include(
    "util",
    "reservation-service",
    "payment-service",
    "loyalty-service",
    "gateway-service",
)
