package com.wrathenn.stats.service

import com.wrathenn.util.spring.ApiExceptionHandler
import com.wrathenn.util.spring.HealthController
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import


@SpringBootApplication
@Import(
    HealthController::class,
    ApiExceptionHandler::class,
)
class StatsServiceApplication


fun main(args: Array<String>) {
    runApplication<StatsServiceApplication>(*args)
}
