package com.wrathenn.gateway.service.services

import com.wrathenn.gateway.service.clients.LoyaltyClient
import com.wrathenn.gateway.service.clients.PaymentClient
import com.wrathenn.gateway.service.clients.ReservationClient
import com.wrathenn.gateway.service.models.*
import com.wrathenn.util.models.loyalty.Loyalty
import org.springframework.stereotype.Service

@Service
class UserService(
    private val reservationClient: ReservationClient,
    private val loyaltyClient: LoyaltyClient,
    private val paymentClient: PaymentClient,
) {
    fun getUserInfo(username: String): UserDto {
        val reservations = reservationClient.getReservationsWithFallback(username).map { reservationAndHotel ->
            val reservation = reservationAndHotel.reservation
            val hotel = reservationAndHotel.hotel
            val paymentUid = reservationAndHotel.reservation.paymentUid
            val payment = paymentClient.getPayment(paymentUid)

            ReservationInfoDto.fromModels(reservation, hotel, payment)
        }

        val loyalty = loyaltyClient.getLoyaltyWithFallback(username)?.let { loyalty: Loyalty ->
            LoyaltyShortDto(
                status = loyalty.status,
                discount = loyalty.discount,
            )
        } ?: EmptyLoyaltyShortDto

        return UserDto(
            reservations = reservations,
            loyalty = loyalty,
        )
    }
}