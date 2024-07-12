package com.wrathenn.gateway.service.v1

import com.wrathenn.gateway.service.models.ReservationDto
import com.wrathenn.gateway.service.models.ReservationInfoDto
import com.wrathenn.gateway.service.services.ReservationsService
import com.wrathenn.util.models.reservation.ReservationRequest
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/api/v1/reservations")
class ReservationsController(
    private val reservationsService: ReservationsService,
) {
    @GetMapping("/{reservationUid}")
    fun getInfo(
        @RequestHeader("X-User-Name") username: String,
        @PathVariable reservationUid: UUID,
    ): ReservationInfoDto {
        return reservationsService.getReservation(reservationUid)
    }

    @GetMapping
    fun getAllInfos(
        @RequestHeader("X-User-Name") username: String,
    ): List<ReservationInfoDto> {
        return reservationsService.getReservations(username)
    }

    @PostMapping
    fun reserveHotel(
        @RequestHeader("X-User-Name") username: String,
        @RequestBody request: ReservationRequest
    ): ReservationDto {
        return reservationsService.createReservation(username, request)
    }

    @DeleteMapping("{reservationUid}")
    fun cancelReservation(
        @RequestHeader("X-User-Name") username: String,
        @PathVariable reservationUid: UUID,
    ): ResponseEntity<Unit> {
        reservationsService.cancelReservation(username, reservationUid)
        // 204!
        return ResponseEntity(HttpStatusCode.valueOf(204))
    }
}
