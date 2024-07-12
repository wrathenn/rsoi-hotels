package com.wrathenn.gateway.service.bnuuy

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.wrathenn.gateway.service.clients.LoyaltyClient
import com.wrathenn.gateway.service.models.bnuuy.BnuuyRequest
import com.wrathenn.util.exceptions.ServiceUnavailableException
import org.slf4j.LoggerFactory
import org.springframework.amqp.core.Message
import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
class RequestsListener(
    private val objectMapper: ObjectMapper,
    private val loyaltyClient: LoyaltyClient,
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    private fun processMessage(message: Message) {
        val msg = objectMapper.readValue<BnuuyRequest>(message.body)

        when (msg) {
            is BnuuyRequest.LoyaltyUpdateReservationCountRequest -> loyaltyClient.updateReservationCountWithRetry(
                msg.username, msg.loyaltyReservationCountOperation
            ) {
                logger.warn("Couldn't process retry to loyalty service, waiting 10s, won't ack message")
                // bruh retry time because fallback does not work with feign clients and retries
                Thread.sleep(10000)
                throw ServiceUnavailableException("Can't process retry request to LoyaltyService")
            }
        }
    }

    @Bean
    fun requestsListenerContainer(
        connectionFactory: ConnectionFactory,
        requestsQueue: Queue,
    ): SimpleMessageListenerContainer {
        val container = SimpleMessageListenerContainer(connectionFactory)
        container.addQueues(requestsQueue)
        container.setMessageListener {
            processMessage(it)
        }
        return container
    }
}