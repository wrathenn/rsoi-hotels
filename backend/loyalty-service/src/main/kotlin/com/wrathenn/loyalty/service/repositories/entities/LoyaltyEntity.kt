package com.wrathenn.loyalty.service.repositories.entities

import com.wrathenn.util.models.loyalty.Loyalty
import com.wrathenn.util.models.loyalty.LoyaltyStatus

data class LoyaltyEntity(
    val id: Long,
    val username: String,
    val reservationCount: Int,
    val status: LoyaltyStatus,
    val discount: Int,
) {
    companion object Converters {
        fun LoyaltyEntity.toModel() = Loyalty(
            id = id,
            username = username,
            reservationCount = reservationCount,
            status = status,
            discount = discount,
        )

        fun Loyalty.toEntity() = LoyaltyEntity(
            id = id,
            username = username,
            reservationCount = reservationCount,
            status = status,
            discount = discount,
        )
    }
}
