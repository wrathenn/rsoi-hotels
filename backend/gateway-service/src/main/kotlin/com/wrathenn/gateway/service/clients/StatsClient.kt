package com.wrathenn.gateway.service.clients

import com.wrathenn.util.models.statistics.Stat
import org.slf4j.LoggerFactory
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.*

@FeignClient(
    value = "stats-service",
    url = "\${app.interop.stats-client-api}",
    fallback = StatsClientFallback::class
)
@Primary
interface StatsClient {
    @GetMapping("/api/stats/{paymentUid}")
    fun getStats(): List<Stat>
}

@Component
class StatsClientFallback: StatsClient {
    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun getStats(): List<Stat> {
        logger.warn("Unsuccessful getStats, using empty list")
        return emptyList()
    }
}
