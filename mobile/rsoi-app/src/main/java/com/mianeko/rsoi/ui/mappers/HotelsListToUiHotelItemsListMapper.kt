package com.mianeko.rsoi.ui.mappers

import com.mianeko.rsoi.domain.entities.Hotel
import com.mianeko.rsoi.ui.entities.UiHotelItem

internal class HotelsListToUiHotelItemsListMapper: (List<Hotel>) -> List<UiHotelItem> {
    override fun invoke(hotels: List<Hotel>): List<UiHotelItem> {
        return hotels.map { 
            UiHotelItem(
                uuid = it.id,
                name = it.name,
                city = it.city,
                country = it.country,
                rating = it.rating,
                price = it.price,
                address = it.address,
            )
        }
    }
}
