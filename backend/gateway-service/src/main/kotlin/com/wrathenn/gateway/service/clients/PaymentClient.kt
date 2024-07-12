package com.wrathenn.gateway.service.clients

import com.wrathenn.util.models.payment.Payment
import com.wrathenn.util.models.payment.PaymentCreate
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.*
import java.util.*

@FeignClient(
    value = "payment-service",
    url = "\${app.interop.payment-client-api}",
    fallback = PaymentClientFallback::class
)
@Primary
interface PaymentClient {
    @GetMapping("/payments/{paymentUid}")
    fun getPayment(@PathVariable paymentUid: UUID): Payment?

    @PostMapping("/payments")
    fun createPayment(@RequestBody paymentCreate: PaymentCreate): Payment

    @PutMapping("/payments/{paymentUid}")
    fun cancelPayment(@PathVariable paymentUid: UUID): Payment
}

@Component
class PaymentClientFallback: PaymentClient {
    private val unavailableMessage = "Payment Service unavailable"

    override fun getPayment(paymentUid: UUID): Payment? {
        return null
    }

    override fun createPayment(paymentCreate: PaymentCreate): Payment {
        TODO("Not yet implemented")
    }

    override fun cancelPayment(paymentUid: UUID): Payment {
        TODO("Not yet implemented")
    }
}
