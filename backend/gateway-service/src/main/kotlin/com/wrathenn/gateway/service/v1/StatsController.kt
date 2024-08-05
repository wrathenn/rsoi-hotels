package com.wrathenn.gateway.service.v1

import com.wrathenn.gateway.service.clients.StatsClient
import com.wrathenn.gateway.service.models.StatDto
import com.wrathenn.gateway.service.security.AuthContext
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/stats")
@CrossOrigin
class StatsController(
    private val statsClient: StatsClient,
    private val authContext: AuthContext,
) {
    @GetMapping
    fun getStats(): List<StatDto> {
        val username = authContext.getCurrentPrincipal().userClaim.id
        return statsClient.getStats().map { StatDto.fromModel(it) }
    }
}
