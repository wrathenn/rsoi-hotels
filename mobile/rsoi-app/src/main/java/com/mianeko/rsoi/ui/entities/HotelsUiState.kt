package com.mianeko.rsoi.ui.entities

internal data class HotelsUiState(
    val lastLoadedPage: Int = 0,
    val pageSize: Int = DEFAULT_PAGE_SIZE,
    val hotels: List<UiHotelItem> = emptyList(),
    val isError: Boolean = false,
) {
    companion object {
        private const val DEFAULT_PAGE_SIZE = 5
    }
}
