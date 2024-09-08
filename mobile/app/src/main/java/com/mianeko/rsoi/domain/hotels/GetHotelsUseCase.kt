package com.mianeko.rsoi.domain.hotels

import com.mianeko.rsoi.domain.entities.Hotel
import com.mianeko.rsoi.domain.repositories.HotelsRepository

internal interface GetHotelsUseCase {
    suspend fun getHotels(page: Int, size: Int): List<Hotel>?
}

internal class GetHotelsUseCaseImpl(
    private val hotelsRepository: HotelsRepository
): GetHotelsUseCase {
    
    override suspend fun getHotels(page: Int, size: Int): List<Hotel>? {
        return hotelsRepository.getHotels(page, size)
    }
}
