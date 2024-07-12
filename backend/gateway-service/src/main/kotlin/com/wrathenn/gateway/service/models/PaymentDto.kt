package com.wrathenn.gateway.service.models

import com.wrathenn.util.models.payment.PaymentStatus

sealed interface BasePaymentDto

data class PaymentDto(
    val status: PaymentStatus,
    val price: Int,
) : BasePaymentDto

data object EmptyPaymentDto : BasePaymentDto


