package com.mianeko.rsoi.ui.reservations

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.mianeko.rsoi.R
import com.mianeko.rsoi.data.services.ReservationApiService
import com.mianeko.rsoi.databinding.ReservationListBinding
import com.mianeko.rsoi.domain.entities.ReservationInfo
import com.mianeko.rsoi.domain.entities.ReservationStatus
import com.mianeko.rsoi.ui.navigation.HotelsNavAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject

class ReservationsFragment: Fragment(R.layout.reservation_list) {

    private var _binding: ReservationListBinding? = null
    private val binding: ReservationListBinding
        get() = _binding ?: throw IllegalStateException("Binding can't be null")

    private var hotelsAdapter: HotelsNavAdapter? = null
    private val reservationsFlow: MutableStateFlow<List<ReservationInfo>> = MutableStateFlow(emptyList())
    private val reservationsApi: ReservationApiService by inject()
    
    fun setAdapter(adapter: HotelsNavAdapter) {
        hotelsAdapter = adapter
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = ReservationListBinding.inflate(inflater)
        binding.recyclerview.layoutManager = LinearLayoutManager(context)
        binding.recyclerview.adapter = ReservationsAdapter(::onCancelReservationClicked)
        lifecycleScope.launch {
            reservationsFlow.collect {
                binding.recyclerview.isVisible = true
                binding.errorText.isVisible = false
                (binding.recyclerview.adapter as ReservationsAdapter).submitItems(it)
            }
        }
        binding.hotelsNavButton.setOnClickListener { 
            hotelsAdapter?.showHotels()
        }
        binding.accountButton.setOnClickListener {
            hotelsAdapter?.authorize()
        }
        binding.loyaltyNavButton.setOnClickListener {
            hotelsAdapter?.showLoyaltyInfo()
        }
        binding.reservationsNavButton.setOnClickListener { 
            hotelsAdapter?.showReservations()
        }
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        lifecycleScope.launch(Dispatchers.IO) {
            val sharedPref = activity?.getSharedPreferences("user_prefs", Context.MODE_PRIVATE) ?: run {
                withContext(Dispatchers.Main) {
                    binding.errorText.isVisible = true
                    binding.recyclerview.isVisible = false
                }
                return@launch
            }
            val token = sharedPref.getString("access_token", "")
            val reservations = reservationsApi.getReservations("Bearer $token")
            if (!reservations.isSuccessful || reservations.body() == null) {
                withContext(Dispatchers.Main) {
                    binding.recyclerview.isVisible = false
                    binding.errorText.isVisible = true
                }
            } else {
                val info = reservations.body()!!
                reservationsFlow.value = info
            }
        }
    }

    private fun onCancelReservationClicked(reservation: ReservationInfo) {
        val sharedPref = activity?.getSharedPreferences("user_prefs", Context.MODE_PRIVATE) ?: run {
            binding.errorText.isVisible = true
            binding.recyclerview.isVisible = false
            return
        }
        val token = sharedPref.getString("access_token", "")
        lifecycleScope.launch(Dispatchers.IO) {
            val result = reservationsApi.deleteReservation("Bearer $token", reservation.reservationUid)
            if (!result.isSuccessful) {
                Log.e(TAG, "Could not delete reservation, ${result.message()}")
                withContext(Dispatchers.Main) {
                    binding.recyclerview.isVisible = false
                    binding.errorText.isVisible = true
                }
            } else {
                withContext(Dispatchers.Main) {
                    (binding.recyclerview.adapter as ReservationsAdapter).changeItemState(reservation, ReservationStatus.CANCELED)
                }
            }
        }
//        hotelsAdapter?.createReservation(hotel)
//        Toast.makeText(context, "Clicked on ${hotel.name}", Toast.LENGTH_LONG).show()
    }

    companion object {
        private const val TAG = "ReservationsFragment"
        fun newInstance(): ReservationsFragment {
            return ReservationsFragment()
        }
    }
}
