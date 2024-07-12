package com.wrathenn.reservation.service.repositories.entities

import com.wrathenn.util.models.reservation.Hotel
import java.util.*

data class HotelEntity(
    val id: Long,
    val hotelUid: UUID,
    val name: String,
    val country: String,
    val city: String,
    val address: String,
    val stars: Int?,
    val price: Int,
) {
    companion object Converters {
        fun HotelEntity.toModel() = Hotel(
            id = id,
            hotelUid = hotelUid,
            name = name,
            country = country,
            city = city,
            address = address,
            stars = stars,
            price = price,
        )

        fun Hotel.toEntity() = HotelEntity(
            id = id,
            hotelUid = hotelUid,
            name = name,
            country = country,
            city = city,
            address = address,
            stars = stars,
            price = price,
        )
    }
}
