package com.mianeko.rsoi.data.entities

internal data class ApiHotelResponse(
    val page: Int,
    val pageSize: Int,
    val totalElements: Int,
    val items: List<ApiHotel>,
)
