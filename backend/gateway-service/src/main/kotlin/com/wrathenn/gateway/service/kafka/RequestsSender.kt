package com.wrathenn.gateway.service.kafka

import com.wrathenn.gateway.service.models.bnuuy.KafkaRequest
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class RequestsSender(
    private val kafkaTemplate: KafkaTemplate<String, Any>,
    @Value("\${kafka.topic}") private val topicName: String,
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    fun sendRequest(request: KafkaRequest) {
        val future = kafkaTemplate.send(topicName, request)
        future.whenComplete { result, ex ->
            if (ex == null) {
                logger.debug("Sent request=[$request] with offset=[" + result.recordMetadata.offset() + "]")
            } else {
                logger.error("Failed to send request=[$request], 'cause of ${ex.message}")
            }
        }
    }
}