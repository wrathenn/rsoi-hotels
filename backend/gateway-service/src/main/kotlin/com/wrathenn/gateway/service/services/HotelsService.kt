package com.wrathenn.gateway.service.services

import com.wrathenn.gateway.service.clients.ReservationClient
import com.wrathenn.gateway.service.models.HotelDto
import com.wrathenn.gateway.service.models.HotelDto.Converters.toDto
import com.wrathenn.util.models.Page
import org.springframework.stereotype.Component

@Component
class HotelsService(
    private val reservationClient: ReservationClient,
) {
    fun getHotelsPaged(page: Int, size: Int): Page<HotelDto> {
        val hotelsPage = reservationClient.pagedHotels(page, size)
        return hotelsPage.map { it.toDto() }
    }
}
