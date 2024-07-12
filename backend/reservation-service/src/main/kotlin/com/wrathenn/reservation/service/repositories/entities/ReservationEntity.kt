package com.wrathenn.reservation.service.repositories.entities

import com.wrathenn.reservation.service.repositories.entities.HotelEntity.Converters.toModel
import com.wrathenn.reservation.service.repositories.entities.ReservationEntity.Converters.toModel
import com.wrathenn.util.models.reservation.Reservation
import com.wrathenn.util.models.reservation.ReservationStatus
import com.wrathenn.util.models.reservation.ReservationWithHotel
import org.jdbi.v3.core.mapper.Nested
import java.time.Instant
import java.util.UUID

data class ReservationEntity(
    val id: Long,
    val reservationUid: UUID,
    val username: String,
    val paymentUid: UUID,
    val hotelId: Long,
    val status: ReservationStatus,
    val startDate: Instant?,
    val endDate: Instant?,
) {
    companion object Converters {
        fun ReservationEntity.toModel() = Reservation(
            id = id,
            reservationUid = reservationUid,
            username = username,
            paymentUid = paymentUid,
            hotelId = hotelId,
            status = status,
            startDate = startDate,
            endDate = endDate,
        )

        fun Reservation.toEntity() = ReservationEntity(
            id = id,
            reservationUid = reservationUid,
            username = username,
            paymentUid = paymentUid,
            hotelId = hotelId,
            status = status,
            startDate = startDate,
            endDate = endDate,
        )
    }
}

data class ReservationWithHotelEntity(
    @Nested("r")
    val reservation: ReservationEntity,
    @Nested("h")
    val hotel: HotelEntity,
) {
    companion object Converters {
        fun ReservationWithHotelEntity.toModel() = ReservationWithHotel(
            reservation = reservation.toModel(),
            hotel = hotel.toModel(),
        )
    }
}

