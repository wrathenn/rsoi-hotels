package com.wrathenn.reservation.service.api

import com.wrathenn.reservation.service.services.HotelsService
import com.wrathenn.util.db.transact
import com.wrathenn.util.exceptions.ApiException
import com.wrathenn.util.exceptions.ResourceNotFoundException
import com.wrathenn.util.models.Page
import com.wrathenn.util.models.reservation.Hotel
import org.jdbi.v3.core.Jdbi
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.util.*

@RestController
@RequestMapping("/hotels")
class HotelsController(
    private val jdbi: Jdbi,
    private val hotelsService: HotelsService,
) {
    @GetMapping
    fun getPaged(
        @RequestParam page: Int,
        @RequestParam size: Int,
    ): Page<Hotel> = jdbi.transact {
        hotelsService.getPaged(page, size)
    }

    @GetMapping("/{hotelUid}")
    fun getHotel(@PathVariable hotelUid: UUID): Hotel = jdbi.transact {
        hotelsService.getHotel(hotelUid)
    }

    @GetMapping("/cost")
    fun getHotelCost(
        @RequestParam hotelUid: UUID,
        @RequestParam from: LocalDate,
        @RequestParam to: LocalDate,
    ): Long = jdbi.transact {
        hotelsService.getHotelCost(hotelUid, from, to)
    }
}
