package com.wrathenn.gateway.service.bnuuy

import com.fasterxml.jackson.databind.ObjectMapper
import com.wrathenn.gateway.service.models.bnuuy.BnuuyRequest
import org.springframework.amqp.core.Exchange
import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component

@Component
class RequestsSender(
    private val rabbitTemplate: RabbitTemplate,
    private val objectMapper: ObjectMapper,
    private val requestsExchange: Exchange,
) {
    fun sendRequest(request: BnuuyRequest) {
        val message = Message(
            objectMapper.writeValueAsBytes(request)
        )
        rabbitTemplate.send(requestsExchange.name, message)
    }
}