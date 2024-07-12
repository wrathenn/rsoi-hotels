package com.wrathenn.reservation.service.api

import com.wrathenn.util.db.transact
import com.wrathenn.util.models.reservation.Reservation
import com.wrathenn.reservation.service.services.ReservationsService
import com.wrathenn.util.models.reservation.ReservationCreate
import com.wrathenn.util.models.reservation.ReservationWithHotel
import org.jdbi.v3.core.Jdbi
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/reservations")
class ReservationsController(
    private val jdbi: Jdbi,
    private val reservationsService: ReservationsService,
) {
    @GetMapping("{reservationUid}")
    fun getReservation(
        @PathVariable reservationUid: UUID,
    ): ReservationWithHotel = jdbi.transact {
        reservationsService.getReservation(reservationUid)
    }

    @GetMapping
    fun getReservations(
        @RequestHeader("X-User-Name") username: String
    ): List<ReservationWithHotel> = jdbi.transact {
        reservationsService.getReservationsByUsername(username)
    }

    @PostMapping
    fun createReservation(
        @RequestBody reservationCreate: ReservationCreate
    ): Reservation = jdbi.transact {
        reservationsService.createReservation(reservationCreate)
    }

    @PutMapping("{reservationUid}")
    fun cancelReservation(
        @PathVariable reservationUid: UUID,
    ): Reservation = jdbi.transact {
        reservationsService.cancelReservation(reservationUid)
    }
}
