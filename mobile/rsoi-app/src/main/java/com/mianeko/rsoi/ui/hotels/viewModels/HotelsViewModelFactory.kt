package com.mianeko.rsoi.ui.hotels.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mianeko.rsoi.domain.repositories.HotelsRepository
import com.mianeko.rsoi.ui.mappers.HotelsListToUiHotelItemsListMapper

internal class HotelsViewModelFactory(
    private val hotelsRepository: HotelsRepository,
    private val hotelsListToUiHotelItemsListMapper: HotelsListToUiHotelItemsListMapper,
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HotelsViewModel(
            hotelsRepository = hotelsRepository,
            hotelsListToUiHotelItemsListMapper = hotelsListToUiHotelItemsListMapper,
        ) as T
    }
}

