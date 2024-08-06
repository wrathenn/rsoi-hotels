package com.wrathenn.gateway.service

import com.wrathenn.util.spring.ApiExceptionHandler
import com.wrathenn.util.spring.HealthController
import com.wrathenn.util.spring.ObjectMapperConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Import


@SpringBootApplication
@EnableFeignClients
@Import(
    HealthController::class,
    ApiExceptionHandler::class,
    ObjectMapperConfiguration::class,
)
class GatewayServiceApplication

fun main(args: Array<String>) {
    runApplication<GatewayServiceApplication>(*args)
}
