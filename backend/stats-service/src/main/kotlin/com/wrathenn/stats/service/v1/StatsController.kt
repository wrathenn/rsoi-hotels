package com.wrathenn.stats.service.v1

import com.wrathenn.stats.service.services.StatsService
import com.wrathenn.util.models.statistics.Stat
import com.wrathenn.util.models.statistics.StatData
import org.springframework.web.bind.annotation.*

@RestController
class StatsController(
    private val statsService: StatsService,
) {
    @GetMapping
    fun getAllStats(): List<Stat<*>> {
        return statsService.getStats(StatData.Type.entries)
    }
}
