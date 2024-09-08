package com.mianeko.rsoi

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.mianeko.rsoi.ui.AuthorizationFragment
import com.mianeko.rsoi.ui.HotelsFragment
import com.mianeko.rsoi.ui.book.BookFragment
import com.mianeko.rsoi.ui.loyalty.LoyaltyFragment
import com.mianeko.rsoi.ui.navigation.HotelsNavAdapter
import com.mianeko.rsoi.ui.navigation.HotelsNavAdapterImpl
import com.mianeko.rsoi.ui.reservations.ReservationsFragment

class MainActivity : FragmentActivity(R.layout.fragment_container) {
    
    private val hotelsNavAdapter: HotelsNavAdapter by lazy { HotelsNavAdapterImpl(supportFragmentManager) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupFragment()
    }

    private fun setupFragment() {
        if (supportFragmentManager.fragments.size == 0) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HotelsFragment.newInstance())
                .commitAllowingStateLoss()
        }
        supportFragmentManager.addFragmentOnAttachListener { _, fragment ->
            when (fragment) {
                is HotelsFragment -> {
                    fragment.setAdapter(hotelsNavAdapter)
                }
                is AuthorizationFragment -> {
                    fragment.setAdapter(hotelsNavAdapter)
                }
                is BookFragment -> {
                    fragment.setAdapter(hotelsNavAdapter)
                }
                is LoyaltyFragment -> {
                    fragment.setAdapter(hotelsNavAdapter)
                }
                is ReservationsFragment -> {
                    fragment.setAdapter(hotelsNavAdapter)
                }
            }
        }
    }

}
