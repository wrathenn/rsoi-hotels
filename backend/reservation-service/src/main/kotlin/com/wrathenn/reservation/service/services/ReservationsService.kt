package com.wrathenn.reservation.service.services

import com.wrathenn.util.models.reservation.Reservation
import com.wrathenn.reservation.service.repositories.ReservationsRepository
import com.wrathenn.util.models.reservation.ReservationCreate
import com.wrathenn.util.models.reservation.ReservationWithHotel
import org.jdbi.v3.core.Handle
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.RequestParam
import java.time.LocalDate
import java.util.*

interface ReservationsService {
    context(Handle) fun getReservation(reservationUid: UUID): ReservationWithHotel
    context(Handle) fun getReservationsByUsername(username: String): List<ReservationWithHotel>
    context(Handle) fun createReservation(reservationCreate: ReservationCreate): Reservation
    context(Handle) fun cancelReservation(reservationUid: UUID): Reservation
    context(Handle) fun getReservationCountForInterval(hotelId: Int, from: LocalDate, to: LocalDate): Int
}

@Component
class ReservationsServiceImpl(
    private val reservationsRepository: ReservationsRepository,
) : ReservationsService {
    context(Handle) override fun getReservation(reservationUid: UUID): ReservationWithHotel {
        return reservationsRepository.getReservation(reservationUid)
    }

    context(Handle)
    override fun getReservationsByUsername(username: String): List<ReservationWithHotel> {
        return reservationsRepository.getReservationsByUsername(username)
    }

    context(Handle) override fun createReservation(reservationCreate: ReservationCreate): Reservation {
        return reservationsRepository.createReservation(reservationCreate)
    }

    context(Handle) override fun cancelReservation(reservationUid: UUID): Reservation {
        return reservationsRepository.cancelReservation(reservationUid)
    }

    context(Handle) override fun getReservationCountForInterval(hotelId: Int, from: LocalDate, to: LocalDate): Int {
        return reservationsRepository.getReservationCountForInterval(hotelId, from, to)
    }
}
