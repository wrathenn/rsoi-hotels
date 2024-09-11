package com.mianeko.rsoi.domain.entities

import java.util.UUID

data class Hotel(
    val id: UUID,
    val name: String,
    val city: String,
    val country: String,
    val rating: Int,
    val price: Int,
    val address: String,
)

data class HotelShort(
    val hotelUid: UUID,
    val name: String,
    val fullAddress: String,
    val stars: Int?,
)
