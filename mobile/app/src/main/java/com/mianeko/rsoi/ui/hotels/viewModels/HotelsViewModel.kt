package com.mianeko.rsoi.ui.hotels.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mianeko.rsoi.domain.repositories.HotelsRepository
import com.mianeko.rsoi.ui.entities.HotelsUiState
import com.mianeko.rsoi.ui.mappers.HotelsListToUiHotelItemsListMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

internal class HotelsViewModel(
    private val hotelsRepository: HotelsRepository,
    private val hotelsListToUiHotelItemsListMapper: HotelsListToUiHotelItemsListMapper,
): ViewModel() {

    private val _uiState: MutableStateFlow<HotelsUiState> = MutableStateFlow(HotelsUiState())
    val uiState: StateFlow<HotelsUiState> = _uiState
    
    fun getHotels() {
        Log.d(TAG, "Get hotels")
        viewModelScope.launch(Dispatchers.IO) {
            val page = uiState.value.lastLoadedPage + 1     
            val result = hotelsRepository.getHotels(page, uiState.value.pageSize)
            _uiState.value = uiState.value.copy(
                lastLoadedPage = page,
                hotels = result?.let(hotelsListToUiHotelItemsListMapper) ?: emptyList(),
                isError = result == null,
            )
        }
    }
    
    companion object {
        private const val TAG = "HotelsViewModel"
    }
}
