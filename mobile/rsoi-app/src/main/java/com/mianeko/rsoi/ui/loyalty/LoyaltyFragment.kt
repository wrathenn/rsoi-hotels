package com.mianeko.rsoi.ui.loyalty

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.mianeko.rsoi.R
import com.mianeko.rsoi.data.services.LoyaltyApiService
import com.mianeko.rsoi.databinding.LoyaltyFragmentBinding
import com.mianeko.rsoi.databinding.NavBarLayoutBinding
import com.mianeko.rsoi.ui.navigation.HotelsNavAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject

class LoyaltyFragment: Fragment(R.layout.loyalty_fragment) {
    private var _binding: LoyaltyFragmentBinding? = null
    private val binding get() = _binding!!
    private var _navBinding: NavBarLayoutBinding? = null
    private val navBinding get() = _navBinding!!

    private var hotelsAdapter: HotelsNavAdapter? = null
    private val loyaltyApi: LoyaltyApiService by inject()

    fun setAdapter(adapter: HotelsNavAdapter) {
        hotelsAdapter = adapter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LoyaltyFragmentBinding.inflate(inflater, container, false)
        _navBinding = NavBarLayoutBinding.bind(binding.root)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navBinding.hotelsNavButton.setOnClickListener { 
            hotelsAdapter?.showHotels()
        }
        navBinding.accountButton.setOnClickListener { 
            hotelsAdapter?.authorize()
        }
        navBinding.reservationsNavButton.setOnClickListener { 
            hotelsAdapter?.showReservations()
        }
        navBinding.loyaltyNavButton.setOnClickListener { 
            hotelsAdapter?.showLoyaltyInfo()
        }
        lifecycleScope.launch(Dispatchers.IO) {
            val sharedPref = activity?.getSharedPreferences("user_prefs", Context.MODE_PRIVATE) ?: run {
                withContext(Dispatchers.Main) {
                    binding.errorText.isVisible = true
                }
                return@launch
            }
            val token = sharedPref.getString("access_token", "")
            val loyalty = loyaltyApi.getLoyaltyInfo("Bearer $token")
            if (!loyalty.isSuccessful || loyalty.body() == null) {
                withContext(Dispatchers.Main) {
                    binding.loyaltyGroup.isVisible = false
                    binding.errorText.isVisible = true
                }
            } else {
                val info = loyalty.body()!!
                withContext(Dispatchers.Main) {
                    binding.loyaltyGroup.isVisible = true
                    binding.errorText.isVisible = false
                    binding.status.text = info.status
                    binding.discount.text = "${info.discount}%"
                    binding.reservationCount.text = info.reservationCount.toString()
                }
            }
        }
    }
    
    companion object {
        fun newInstance(): LoyaltyFragment {
            return LoyaltyFragment()
        }
    }
}
