rootProject.name = "rsoi"

pluginManagement {
    plugins {
        id("org.jetbrains.kotlin.jvm") version "1.9.20"
    }
}

include(
    "util",
    "reservation-service",
    "payment-service",
    "loyalty-service",
    "gateway-service",
    "stats-service",
)
