package com.wrathenn.payment.service.repositories.entities

import com.wrathenn.util.models.payment.Payment
import com.wrathenn.util.models.payment.PaymentStatus
import java.util.*

data class PaymentEntity(
    val id: Long,
    val paymentUid: UUID,
    val status: PaymentStatus,
    val price: Int,
) {
    companion object Converters {
        fun PaymentEntity.toModel() = Payment(
            id = id,
            paymentUid = paymentUid,
            status = status,
            price = price,
        )

        fun Payment.toEntity() = PaymentEntity(
            id = id,
            paymentUid = paymentUid,
            status = status,
            price = price,
        )
    }
}
