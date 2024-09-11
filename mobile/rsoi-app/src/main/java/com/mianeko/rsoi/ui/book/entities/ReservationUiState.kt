package com.mianeko.rsoi.ui.book.entities

import com.mianeko.rsoi.domain.entities.ReservationResultInfo
import com.mianeko.rsoi.ui.entities.UiHotelItem

data class ReservationUiState(
    val hotel: UiHotelItem? = null,
    val startDate: String? = null,
    val endDate: String? = null,
    val isInProgress: Boolean = false,
    val reservationResultInfo: ReservationResultInfo? = null,
    val isFullInfo: Boolean = false,
    val isError: Boolean = false,
)
