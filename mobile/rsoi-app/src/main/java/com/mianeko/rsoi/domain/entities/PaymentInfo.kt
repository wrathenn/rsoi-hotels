package com.mianeko.rsoi.domain.entities

data class PaymentInfo(
    val status: PaymentStatus,
    val price: Int,
)
