package com.wrathenn.gateway.service.services

import com.wrathenn.gateway.service.clients.LoyaltyClient
import com.wrathenn.gateway.service.models.LoyaltyDto
import org.springframework.stereotype.Service

@Service
class LoyaltyService(
    private val loyaltyClient: LoyaltyClient,
) {
    fun getLoyalty(username: String): LoyaltyDto {
        val loyalty = loyaltyClient.getLoyalty(username)
        return LoyaltyDto(
            status = loyalty.status,
            discount = loyalty.discount,
            reservationCount = loyalty.reservationCount,
        )
    }
}