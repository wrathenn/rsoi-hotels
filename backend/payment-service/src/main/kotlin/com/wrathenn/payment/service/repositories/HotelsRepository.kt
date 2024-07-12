package com.wrathenn.payment.service.repositories

import com.wrathenn.payment.service.repositories.entities.PaymentEntity
import com.wrathenn.payment.service.repositories.entities.PaymentEntity.Converters.toModel
import com.wrathenn.util.exceptions.ApiException
import com.wrathenn.util.exceptions.ResourceNotFoundException
import com.wrathenn.util.models.payment.Payment
import com.wrathenn.util.models.payment.PaymentCreate
import com.wrathenn.util.models.payment.PaymentStatus
import org.jdbi.v3.core.Handle
import org.jdbi.v3.core.kotlin.mapTo
import org.springframework.stereotype.Component
import java.lang.Exception
import java.util.*

interface PaymentsRepository {
    context(Handle) fun getPayment(paymentUid: UUID): Payment
    context(Handle) fun createPayment(paymentCreate: PaymentCreate): Payment
    context(Handle) fun cancelPayment(paymentUid: UUID): Payment
}

@Component
class PaymentsRepositoryImpl : PaymentsRepository {
    context(Handle) override fun getPayment(paymentUid: UUID): Payment {
        return select("""
            select id, payment_uid, status, price
            from payment.payment
            where payment_uid = :paymentUid
        """.trimIndent())
            .bind("paymentUid", paymentUid)
            .mapTo<PaymentEntity>()
            .firstOrNull()
            ?.toModel()
            ?: throw ResourceNotFoundException("Оплата с uid = $paymentUid не найдена")
    }

    context(Handle)
    override fun createPayment(paymentCreate: PaymentCreate): Payment {
        return createQuery("""
            insert into payment.payment(payment_uid, status, price)
            values (gen_random_uuid(), :status, :price)
            returning id, payment_uid, status, price
        """.trimIndent())
            .bind("status", PaymentStatus.PAID)
            .bind("price", paymentCreate.price)
            .mapTo<PaymentEntity>()
            .firstOrNull()
            ?.toModel()
            ?: throw ApiException("Ошибка при создании оплаты $paymentCreate")
    }

    context(Handle) override fun cancelPayment(paymentUid: UUID): Payment {
        return createQuery("""
            update payment.payment
            set status = :status
            where payment_uid = :paymentUid
            returning id, payment_uid, status, price
        """.trimIndent())
            .bind("status", PaymentStatus.CANCELED)
            .bind("paymentUid", paymentUid)
            .mapTo<PaymentEntity>()
            .firstOrNull()
            ?.toModel()
            ?: throw ApiException("Ошибка при отмене оплаты $paymentUid")
    }
}
