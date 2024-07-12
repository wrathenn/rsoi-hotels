package com.wrathenn.payment.service.services

import com.wrathenn.payment.service.repositories.PaymentsRepository
import com.wrathenn.util.models.payment.Payment
import com.wrathenn.util.models.payment.PaymentCreate
import org.jdbi.v3.core.Handle
import org.springframework.stereotype.Component
import java.util.UUID

interface PaymentsService {
    context(Handle) fun getPayment(paymentUid: UUID): Payment
    context(Handle) fun createPayment(paymentCreate: PaymentCreate): Payment
    context(Handle) fun cancelPayment(paymentUid: UUID): Payment
}

@Component
class PaymentsServiceImpl(
    private val paymentsRepository: PaymentsRepository,
) : PaymentsService {
    context(Handle) override fun getPayment(paymentUid: UUID): Payment {
        return paymentsRepository.getPayment(paymentUid)
    }

    context(Handle) override fun createPayment(paymentCreate: PaymentCreate): Payment {
        return paymentsRepository.createPayment(paymentCreate)
    }

    context(Handle) override fun cancelPayment(paymentUid: UUID): Payment {
        return paymentsRepository.cancelPayment(paymentUid)
    }
}
