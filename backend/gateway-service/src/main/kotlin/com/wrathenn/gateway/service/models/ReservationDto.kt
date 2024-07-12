package com.wrathenn.gateway.service.models

import com.wrathenn.util.models.payment.Payment
import com.wrathenn.util.models.reservation.Hotel
import com.wrathenn.util.models.reservation.Reservation
import com.wrathenn.util.models.reservation.ReservationStatus
import java.time.LocalDate
import java.time.ZoneOffset
import java.util.*

data class ReservationDto(
    val reservationUid: UUID,
    val hotelUid: UUID,
    val startDate: LocalDate?,
    val endDate: LocalDate?,

    val discount: Int,

    val status: ReservationStatus,

    val payment: PaymentDto,
)

data class ReservationInfoDto(
    val reservationUid: UUID,

    val hotel: HotelShortDto,

    val startDate: LocalDate?,
    val endDate: LocalDate?,
    val status: ReservationStatus,

    val payment: BasePaymentDto,
) {
    companion object {
        fun fromModels(
            reservation: Reservation,
            hotel: Hotel,
            payment: Payment?
        ): ReservationInfoDto {
            return ReservationInfoDto(
                reservationUid = reservation.reservationUid,
                hotel = HotelShortDto(
                    hotelUid = hotel.hotelUid,
                    name = hotel.name,
                    fullAddress = listOf(hotel.country, hotel.city, hotel.address).joinToString(", "),
                    stars = hotel.stars,
                ),
                startDate = reservation.startDate?.let { LocalDate.from(it.atOffset(ZoneOffset.UTC)) },
                endDate = reservation.endDate?.let { LocalDate.from(it.atOffset(ZoneOffset.UTC)) },
                status = reservation.status,
                payment = payment?.let { PaymentDto(
                    status = payment.status,
                    price = payment.price,
                ) } ?: EmptyPaymentDto,
            )
        }
    }
}
