package com.wrathenn.loyalty.service.services

import com.wrathenn.util.models.loyalty.LoyaltyStatus

fun getLoyaltyStatus(reservationCount: Int): LoyaltyStatus {
    return when {
        reservationCount < 10 -> LoyaltyStatus.BRONZE
        reservationCount < 20 -> LoyaltyStatus.SILVER
        else -> LoyaltyStatus.GOLD
    }
}