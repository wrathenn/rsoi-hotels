package com.wrathenn.gateway.service.services

import com.wrathenn.gateway.service.bnuuy.RequestsSender
import com.wrathenn.gateway.service.clients.LoyaltyClient
import com.wrathenn.gateway.service.clients.PaymentClient
import com.wrathenn.gateway.service.clients.ReservationClient
import com.wrathenn.gateway.service.models.PaymentDto
import com.wrathenn.gateway.service.models.ReservationDto
import com.wrathenn.gateway.service.models.ReservationInfoDto
import com.wrathenn.gateway.service.models.bnuuy.BnuuyRequest
import com.wrathenn.util.models.loyalty.LoyaltyReservationCountOperation
import com.wrathenn.util.models.payment.PaymentCreate
import com.wrathenn.util.models.reservation.ReservationCreate
import com.wrathenn.util.models.reservation.ReservationRequest
import com.wrathenn.util.models.reservation.ReservationStatus
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.ZoneOffset
import java.util.UUID

@Service
class ReservationsService(
    private val reservationClient: ReservationClient,
    private val loyaltyClient: LoyaltyClient,
    private val paymentClient: PaymentClient,
    private val requestsSender: RequestsSender,
) {
    fun getReservation(reservationUid: UUID): ReservationInfoDto {
        val reservationAndHotel = reservationClient.getReservation(reservationUid)
        val reservation = reservationAndHotel.reservation
        val hotel = reservationAndHotel.hotel
        val payment = paymentClient.getPayment(reservation.paymentUid)

        return ReservationInfoDto.fromModels(reservation, hotel, payment)
    }

    fun getReservations(username: String): List<ReservationInfoDto> {
        return reservationClient.getReservations(username).map { reservationAndHotel ->
            val reservation = reservationAndHotel.reservation
            val hotel = reservationAndHotel.hotel
            val payment = paymentClient.getPayment(reservation.paymentUid)

            ReservationInfoDto.fromModels(reservation, hotel, payment)
        }
    }

    fun createReservation(username: String, request: ReservationRequest): ReservationDto {
        // Проверить, что отель существует
        val hotel = reservationClient.getHotel(request.hotelUid)
        // Общая сумма бронирования
        val cost = reservationClient.getHotelCost(request.hotelUid, request.startDate, request.endDate)

        val loyalty = loyaltyClient.getLoyalty(username)
        val finalCost = cost * (100 - loyalty.discount) / 100

        // Создать новую запись об оплате
        val payment = paymentClient.createPayment(PaymentCreate(finalCost))
        // Увеличить счетчик бронирований
        val newLoyalty = loyaltyClient.updateReservationCount(username, LoyaltyReservationCountOperation.INCREMENT)

        val reservationCreate = ReservationCreate(
            username = username,
            paymentUid = payment.paymentUid,
            hotelId = hotel.id,
            status = ReservationStatus.PAID,
            startDate = request.startDate.atStartOfDay().toInstant(ZoneOffset.UTC),
            endDate = request.endDate.atStartOfDay().toInstant(ZoneOffset.UTC),
        )
        val reservation = reservationClient.createReservation(reservationCreate)

        return ReservationDto(
            reservationUid = reservation.reservationUid,
            hotelUid = hotel.hotelUid,
            startDate = reservation.startDate?.let { LocalDate.from(it.atOffset(ZoneOffset.UTC)) },
            endDate = reservation.endDate?.let { LocalDate.from(it.atOffset(ZoneOffset.UTC)) },
            discount = loyalty.discount,
            status = reservation.status,
            payment = PaymentDto(
                payment.status,
                payment.price,
            ),
        )
    }

    fun cancelReservation(username: String, reservationUid: UUID) {
        // Отменить бронирование
        val reservation = reservationClient.cancelReservation(reservationUid)
        // Отменить оплату
        val payment = paymentClient.cancelPayment(reservation.paymentUid)

        // Уменьшить счетчик бронирований
        loyaltyClient.updateReservationCountWithRetry(username, LoyaltyReservationCountOperation.DECREMENT) {
            requestsSender.sendRequest(BnuuyRequest.LoyaltyUpdateReservationCountRequest(
                username,
                LoyaltyReservationCountOperation.DECREMENT,
            ))
        }
    }
}