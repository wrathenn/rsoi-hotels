package com.wrathenn.stats.service.kafka

import com.wrathenn.stats.service.services.StatsService
import com.wrathenn.util.models.statistics.StatTemplate
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

@Component
class RequestsListener(
    private val statsService: StatsService,
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @KafkaListener(
        topics = ["\${kafka.topic}"],
        containerFactory = "listenerContainerFactory",
    )
    fun requestsListenerContainer(@Payload payload: StatTemplate<*>) {
        logger.info("Got new stat: $payload")
        statsService.insertStat(payload)
    }
}