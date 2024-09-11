package com.mianeko.rsoi.data.entities

import java.util.UUID

internal data class ApiHotel(
    val hotelUid: UUID,
    val name: String,
    val country: String,
    val city: String,
    val address: String,
    val stars: Int,
    val price: Int,
)
