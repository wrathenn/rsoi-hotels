package com.wrathenn.gateway.service.kafka

import com.wrathenn.gateway.service.clients.LoyaltyClient
import com.wrathenn.gateway.service.models.bnuuy.KafkaRequest
import com.wrathenn.util.exceptions.ServiceUnavailableException
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

@Component
class RequestsListener(
    private val loyaltyClient: LoyaltyClient,
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @KafkaListener(
        topics = ["\${kafka.topic}"],
        containerFactory = "listenerContainerFactory",
    )
    fun requestsListenerContainer(@Payload payload: KafkaRequest) {
        when (payload) {
            is KafkaRequest.LoyaltyUpdateReservationCountRequest -> loyaltyClient.updateReservationCountWithRetry(
                payload.username, payload.loyaltyReservationCountOperation
            ) {
                logger.warn("Couldn't process retry to loyalty service, waiting 10s, won't ack message")
                // retry time because fallback does not work with feign clients and retries
                Thread.sleep(10000)
                throw ServiceUnavailableException("Can't process retry request to LoyaltyService")
            }
        }
    }
}