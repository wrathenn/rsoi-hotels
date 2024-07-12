package com.wrathenn.gateway.service.clients

import com.wrathenn.util.exceptions.ApiException
import com.wrathenn.util.exceptions.ServiceUnavailableException
import com.wrathenn.util.models.Page
import com.wrathenn.util.models.reservation.Hotel
import com.wrathenn.util.models.reservation.Reservation
import com.wrathenn.util.models.reservation.ReservationCreate
import com.wrathenn.util.models.reservation.ReservationWithHotel
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.util.*

@FeignClient(
    name = "ReservationClient",
    url = "\${app.interop.reservation-client-api}",
    fallback = ReservationClientFallback::class,
)
@Primary
interface ReservationClient {
    @GetMapping
    fun throwAsd(): Nothing = throw Exception("asd")

    @GetMapping("/hotels")
    fun pagedHotels(@RequestParam page: Int, @RequestParam size: Int): Page<Hotel>

    @GetMapping("/hotels/{hotelUid}")
    fun getHotel(@PathVariable hotelUid: UUID): Hotel

    @GetMapping("/hotels/cost")
    fun getHotelCost(
        @RequestParam hotelUid: UUID,
        @RequestParam from: LocalDate,
        @RequestParam to: LocalDate
    ): Int

    @GetMapping("/reservations/{reservationUid}")
    fun getReservation(
        @PathVariable reservationUid: UUID,
    ): ReservationWithHotel

    @GetMapping("/reservations")
    fun getReservations(@RequestHeader("X-User-Name") username: String): List<ReservationWithHotel>

    @GetMapping("/reservations")
    fun getReservationsWithFallback(@RequestHeader("X-User-Name") username: String): List<ReservationWithHotel>

    @PostMapping("/reservations")
    fun createReservation(
        @RequestBody reservationCreate: ReservationCreate
    ): Reservation

    @PutMapping("/reservations/{reservationUid}")
    fun cancelReservation(
        @PathVariable reservationUid: UUID,
    ): Reservation
}


@Component
class ReservationClientFallback: ReservationClient {
    private val unavailableMessage = "Loyalty Service unavailable"

    @GetMapping("/hotels")
    override fun pagedHotels(page: Int, size: Int): Page<Hotel> {
        throw ServiceUnavailableException(unavailableMessage)
    }

    override fun getHotel(hotelUid: UUID): Hotel {
        throw ServiceUnavailableException(unavailableMessage)
    }

    override fun getHotelCost(hotelUid: UUID, from: LocalDate, to: LocalDate): Int {
        TODO("Not yet implemented")
    }

    override fun getReservation(reservationUid: UUID): ReservationWithHotel {
        throw ServiceUnavailableException(unavailableMessage)
    }

    override fun getReservations(username: String): List<ReservationWithHotel> {
        throw ServiceUnavailableException(unavailableMessage)
    }

    override fun getReservationsWithFallback(username: String): List<ReservationWithHotel> {
        return emptyList()
    }

    override fun createReservation(reservationCreate: ReservationCreate): Reservation {
        TODO("Not yet implemented")
    }

    override fun cancelReservation(reservationUid: UUID): Reservation {
        TODO("Not yet implemented")
    }

}
