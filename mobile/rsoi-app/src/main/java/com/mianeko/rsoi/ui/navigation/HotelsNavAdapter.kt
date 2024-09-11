package com.mianeko.rsoi.ui.navigation

import androidx.fragment.app.FragmentManager
import com.mianeko.rsoi.R
import com.mianeko.rsoi.ui.AuthorizationFragment
import com.mianeko.rsoi.ui.HotelsFragment
import com.mianeko.rsoi.ui.book.BookFragment
import com.mianeko.rsoi.ui.entities.UiHotelItem
import com.mianeko.rsoi.ui.loyalty.LoyaltyFragment
import com.mianeko.rsoi.ui.reservations.ReservationsFragment

interface HotelsNavAdapter {
    fun showHotels()
    fun createReservation(hotel: UiHotelItem)

    fun showReservations()

    fun showLoyaltyInfo()
    
    fun authorize()
}


internal class HotelsNavAdapterImpl(
    private val fragmentManager: FragmentManager,
): HotelsNavAdapter {
    override fun showHotels() {
        fragmentManager.beginTransaction()
            .replace(R.id.fragment_container, HotelsFragment.newInstance())
            .commitAllowingStateLoss()
    }

    override fun createReservation(hotel: UiHotelItem) {
        fragmentManager.beginTransaction()
            .replace(R.id.fragment_container, BookFragment.newInstance(hotel))
            .commitAllowingStateLoss()
    }

    override fun showReservations() {
        fragmentManager.beginTransaction()
            .replace(R.id.fragment_container, ReservationsFragment.newInstance())
            .commitAllowingStateLoss()
    }

    override fun showLoyaltyInfo() {
        fragmentManager.beginTransaction()
            .replace(R.id.fragment_container, LoyaltyFragment.newInstance())
            .commitAllowingStateLoss()
    }

    override fun authorize() {
        fragmentManager.beginTransaction()
            .replace(R.id.fragment_container, AuthorizationFragment.newInstance())
            .commitAllowingStateLoss()
    }

}
