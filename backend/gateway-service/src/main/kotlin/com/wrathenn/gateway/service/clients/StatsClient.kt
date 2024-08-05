package com.wrathenn.gateway.service.clients

import com.wrathenn.util.models.statistics.Stat
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
    @GetMapping("/stats/{paymentUid}")
    fun getStats(): List<Stat<*>>
}

@Component
class StatsClientFallback: StatsClient {
    override fun getStats(): List<Stat<*>> {
        return emptyList()
    }
}
