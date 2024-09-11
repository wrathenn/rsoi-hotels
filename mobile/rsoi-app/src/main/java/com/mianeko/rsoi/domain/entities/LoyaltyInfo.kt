package com.mianeko.rsoi.domain.entities

data class LoyaltyInfo(
    val status: LoyaltyStatus,
    val discount: Int,
    val reservationCount: Int,
)
