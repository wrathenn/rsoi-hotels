package com.mianeko.rsoi.ui.book.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mianeko.rsoi.domain.entities.ReservationRequest
import com.mianeko.rsoi.domain.repositories.ReservationRepository
import com.mianeko.rsoi.ui.book.entities.ReservationUiState
import com.mianeko.rsoi.ui.entities.UiHotelItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

internal class BookViewModel(
    private val reservationRepository: ReservationRepository,
): ViewModel() {
    private val _uiState: MutableStateFlow<ReservationUiState> = MutableStateFlow(ReservationUiState())
    val uiState: StateFlow<ReservationUiState> = _uiState
    
    fun setHotel(hotel: UiHotelItem) {
        _uiState.value = _uiState.value.copy(hotel = hotel)
    }
    
    fun setStartDate(startDate: String) {
        val state = _uiState.value
        _uiState.value = state.copy(
            startDate = startDate,
            isError = false,
            isFullInfo = state.endDate != null && state.hotel != null,
            reservationResultInfo = null,
        )
    }

    fun setEndDate(endDate: String) {
        val state = _uiState.value
        _uiState.value = state.copy(
            endDate = endDate,
            isError = false,
            isFullInfo = state.startDate != null && state.hotel != null,
            reservationResultInfo = null,
        )
    }

    fun reserve() {
        val state = uiState.value
        if (state.hotel != null && state.startDate != null && state.endDate != null) {
            val request = ReservationRequest(
                hotelUid = state.hotel.uuid,
                startDate = state.startDate,
                endDate = state.endDate,
            )
            _uiState.value = state.copy(
                isInProgress = true,
                isError = false,
                reservationResultInfo = null,
            )
            viewModelScope.launch(Dispatchers.IO) {
                val reservation = reservationRepository.makeReservation(request)
                _uiState.value = state.copy(
                    reservationResultInfo = reservation,
                    isInProgress = false,
                    isError = reservation == null,
                )
            }
        }
    }
    
}
