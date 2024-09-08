package com.mianeko.rsoi.data.mappers

import com.mianeko.rsoi.data.entities.ApiHotelResponse
import com.mianeko.rsoi.domain.entities.Hotel

internal class ApiHotelResponseToHotelsListMapper: (ApiHotelResponse) -> List<Hotel> {
    override fun invoke(response: ApiHotelResponse): List<Hotel> {
        return response.items.map { 
            Hotel(
                id = it.hotelUid,
                name = it.name,
                city = it.city,
                country = it.country,
                rating = it.stars,
                price = it.price,
                address = it.address,
            )
        }
    }
}
