package com.wrathenn.reservation.service.repositories

import com.wrathenn.reservation.service.repositories.entities.HotelEntity
import com.wrathenn.reservation.service.repositories.entities.HotelEntity.Converters.toModel
import com.wrathenn.util.exceptions.ResourceNotFoundException
import com.wrathenn.util.models.Page
import com.wrathenn.util.models.reservation.Hotel
import org.jdbi.v3.core.Handle
import org.jdbi.v3.core.kotlin.mapTo
import org.springframework.stereotype.Component
import java.util.UUID

interface HotelsRepository {
    context(Handle) fun getPaged(page: Int, size: Int): Page<Hotel>
    context(Handle) fun getHotel(hotelUid: UUID): Hotel
}

@Component
class HotelsRepositoryImpl : HotelsRepository {
    context(Handle) override fun getPaged(page: Int, size: Int): Page<Hotel> {
        val totalCount = select("""
            select count(*)
            from reservation.hotel
        """.trimIndent())
            .mapTo<Int>()
            .one()

        // bruh
        val from = (page - 1) * size

        val items = select("""
            select
              id,
              hotel_uid,
              name,
              country,
              city,
              address,
              stars,
              price
            from reservation.hotel
            order by id
            limit :size
            offset :from
        """.trimIndent())
            .bind("size", size)
            .bind("from", from)
            .mapTo<HotelEntity>()
            .list()
            .map { it.toModel() }

        return Page(
            page = page,
            pageSize = size,
            totalElements = totalCount,
            items = items,
        )
    }

    context(Handle) override fun getHotel(hotelUid: UUID): Hotel {
        return select("""
            select id, hotel_uid, name, country, city, address, stars, price
            from reservation.hotel
            where hotel_uid = :hotelUid
        """.trimIndent())
            .bind("hotelUid", hotelUid)
            .mapTo<HotelEntity>()
            .firstOrNull()
            ?.toModel()
            ?: throw ResourceNotFoundException("Отель с uid = $hotelUid не найден")
    }
}
