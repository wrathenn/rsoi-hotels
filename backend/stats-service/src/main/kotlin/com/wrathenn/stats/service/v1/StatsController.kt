package com.wrathenn.stats.service.v1

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.wrathenn.stats.service.services.StatsService
import com.wrathenn.util.models.loyalty.LoyaltyReservationCountOperation
import com.wrathenn.util.models.statistics.Stat
import com.wrathenn.util.models.statistics.StatData
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*
import java.time.Instant
import kotlin.math.log

@RestController
class StatsController(
    private val statsService: StatsService,
    private val objectMapper: ObjectMapper,
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @GetMapping
    fun getAllStats(): List<Stat> {
        val stats = statsService.getStats(StatData.Type.entries)
        return stats
    }
}
