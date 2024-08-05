package com.wrathenn.gateway.service.kafka

import com.wrathenn.util.models.statistics.StatTemplate
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class StatsSender(
    private val kafkaTemplate: KafkaTemplate<String, Any>,
    @Value("\${kafka.topic_stats}") private val topicName: String,
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    fun sendStat(stat: StatTemplate<*>) {
        val future = kafkaTemplate.send(topicName, stat)
        future.whenComplete { result, ex ->
            if (ex == null) {
                logger.debug("Sent stat=[$stat] with offset=[" + result.recordMetadata.offset() + "]")
            } else {
                logger.error("Failed to send request=[$stat], 'cause of ${ex.message}")
            }
        }
    }
}
