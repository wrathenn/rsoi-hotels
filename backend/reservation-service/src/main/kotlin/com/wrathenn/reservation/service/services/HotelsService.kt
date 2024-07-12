package com.wrathenn.reservation.service.services

import com.wrathenn.reservation.service.repositories.HotelsRepository
import com.wrathenn.util.models.Page
import com.wrathenn.util.models.reservation.Hotel
import org.jdbi.v3.core.Handle
import org.springframework.stereotype.Component
import java.time.Duration
import java.time.LocalDate
import java.time.ZoneOffset
import java.util.*

interface HotelsService {
    context (Handle) fun getPaged(page: Int, size: Int): Page<Hotel>
    context (Handle) fun getHotel(hotelUid: UUID): Hotel
    context (Handle) fun getHotelCost(hotelUid: UUID, from: LocalDate, to: LocalDate): Long
}

@Component
class HotelsServiceImpl(
    private val hotelsRepository: HotelsRepository,
) : HotelsService {
    context(Handle) override fun getPaged(page: Int, size: Int): Page<Hotel> {
        return hotelsRepository.getPaged(page, size)
    }

    context(Handle) override fun getHotel(hotelUid: UUID): Hotel {
        return hotelsRepository.getHotel(hotelUid)
    }

    context(Handle)
    override fun getHotelCost(
        hotelUid: UUID,
        from: LocalDate,
        to: LocalDate,
    ): Long {
        val hotel = hotelsRepository.getHotel(hotelUid)
        val daysCount = Duration.between(
            from.atStartOfDay().toInstant(ZoneOffset.UTC),
            to.atStartOfDay().toInstant(ZoneOffset.UTC)
        ).toDays()
        return hotel.price * daysCount
    }
}
