package com.wrathenn.util.models.payment

import java.util.UUID

enum class PaymentStatus {
    PAID,
    CANCELED,
}

data class Payment(
    val id: Long,
    val paymentUid: UUID,
    val status: PaymentStatus,
    val price: Int,
)

data class PaymentCreate(
    val price: Int,
)
