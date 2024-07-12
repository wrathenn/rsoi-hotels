package com.wrathenn.util.models.reservation

import java.time.Instant
import java.util.*

enum class ReservationStatus {
    PAID,
    CANCELED,
}

data class Reservation(
    val id: Long,
    val reservationUid: UUID,
    val username: String,
    val paymentUid: UUID,
    val hotelId: Long,
    val status: ReservationStatus,
    val startDate: Instant?,
    val endDate: Instant?,
)

data class ReservationWithHotel(
    val reservation: Reservation,
    val hotel: Hotel,
)

data class ReservationCreate(
    val username: String,
    val paymentUid: UUID,
    val hotelId: Long,
    val status: ReservationStatus,
    val startDate: Instant?,
    val endDate: Instant?,
)
