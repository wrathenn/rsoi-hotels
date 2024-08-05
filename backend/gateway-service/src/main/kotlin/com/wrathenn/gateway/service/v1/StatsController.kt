package com.wrathenn.gateway.service.v1

import com.wrathenn.gateway.service.clients.StatsClient
import com.wrathenn.gateway.service.models.StatDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController

@RestController("/stats")
class StatsController(
    private val statsClient: StatsClient,
) {
    @GetMapping
    fun getStats(@RequestHeader("X-User-Name") username: String): List<StatDto> {
        return statsClient.getStats().map { StatDto.fromModel(it) }
    }
}
