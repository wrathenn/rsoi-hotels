package com.mianeko.rsoi.ui.book.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mianeko.rsoi.domain.repositories.ReservationRepository

class BookViewModelFactory(
    private val reservationRepository: ReservationRepository,
): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return BookViewModel(
            reservationRepository = reservationRepository,
        ) as T
    }
}
