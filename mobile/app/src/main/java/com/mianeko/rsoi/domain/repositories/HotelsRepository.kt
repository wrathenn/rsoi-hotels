package com.mianeko.rsoi.domain.repositories

import com.mianeko.rsoi.domain.entities.Hotel

internal interface HotelsRepository {
    suspend fun getHotels(page: Int, pageSize: Int): List<Hotel>?
}
