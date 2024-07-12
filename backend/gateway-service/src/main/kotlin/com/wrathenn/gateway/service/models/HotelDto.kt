package com.wrathenn.gateway.service.models

import com.wrathenn.util.models.reservation.Hotel
import java.util.*

data class HotelDto(
    val hotelUid: UUID,
    val name: String,
    val country: String,
    val city: String,
    val address: String,
    val stars: Int?,
    val price: Int,
) {
    companion object Converters {
        fun Hotel.toDto() = HotelDto(
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

data class HotelShortDto(
    val hotelUid: UUID,
    val name: String,
    val fullAddress: String,
    val stars: Int?,
)
