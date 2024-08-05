package com.wrathenn.gateway.service.v1

import com.wrathenn.gateway.service.models.ReservationDto
import com.wrathenn.gateway.service.models.ReservationInfoDto
import com.wrathenn.gateway.service.security.AuthContext
import com.wrathenn.gateway.service.services.ReservationsService
import com.wrathenn.util.models.reservation.ReservationRequest
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/reservations")
@CrossOrigin
class ReservationsController(
    private val reservationsService: ReservationsService,
    private val authContext: AuthContext,
) {
    @GetMapping("/{reservationUid}")
    fun getInfo(
        @PathVariable reservationUid: UUID,
    ): ReservationInfoDto {
        val username = authContext.getCurrentPrincipal().userClaim.id
        return reservationsService.getReservation(reservationUid)
    }

    @GetMapping
    fun getAllInfos(): List<ReservationInfoDto> {
        val username = authContext.getCurrentPrincipal().userClaim.id
        return reservationsService.getReservations(username)
    }

    @PostMapping
    fun reserveHotel(
        @RequestBody request: ReservationRequest
    ): ReservationDto {
        val username = authContext.getCurrentPrincipal().userClaim.id
        return reservationsService.createReservation(username, request)
    }

    @DeleteMapping("{reservationUid}")
    fun cancelReservation(
        @PathVariable reservationUid: UUID,
    ): ResponseEntity<Unit> {
        val username = authContext.getCurrentPrincipal().userClaim.id
        reservationsService.cancelReservation(username, reservationUid)
        // 204!
        return ResponseEntity(HttpStatusCode.valueOf(204))
    }
}
