package com.wrathenn.payment.service.api

import com.wrathenn.payment.service.services.PaymentsService
import com.wrathenn.util.db.transact
import com.wrathenn.util.models.payment.Payment
import com.wrathenn.util.models.payment.PaymentCreate
import org.jdbi.v3.core.Jdbi
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/payments")
class PaymentsController(
    private val jdbi: Jdbi,
    private val paymentsService: PaymentsService,
) {
    @GetMapping("{paymentUid}")
    fun getPayment(@PathVariable paymentUid: UUID): Payment = jdbi.transact {
        paymentsService.getPayment(paymentUid)
    }

    @PostMapping
    fun createPayment(@RequestBody paymentCreate: PaymentCreate): Payment = jdbi.transact {
        paymentsService.createPayment(paymentCreate)
    }

    @PutMapping("{paymentUid}")
    fun cancelPayment(@PathVariable paymentUid: UUID): Payment = jdbi.transact {
        paymentsService.cancelPayment(paymentUid)
    }
}
