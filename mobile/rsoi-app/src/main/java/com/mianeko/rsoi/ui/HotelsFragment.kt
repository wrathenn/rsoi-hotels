package com.mianeko.rsoi.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.mianeko.rsoi.R
import com.mianeko.rsoi.databinding.HotelsListBinding
import com.mianeko.rsoi.ui.entities.UiHotelItem
import com.mianeko.rsoi.ui.hotels.HotelsAdapter
import com.mianeko.rsoi.ui.hotels.viewModels.HotelsViewModel
import com.mianeko.rsoi.ui.hotels.viewModels.HotelsViewModelFactory
import com.mianeko.rsoi.ui.navigation.HotelsNavAdapter
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject


class HotelsFragment: Fragment(R.layout.hotels_list) {

    private var _binding: HotelsListBinding? = null
    private val binding: HotelsListBinding
        get() = _binding ?: throw IllegalStateException("Binding can't be null")

    private val hotelsViewModelFactory: HotelsViewModelFactory by inject()
    private val viewModel: HotelsViewModel by viewModels { hotelsViewModelFactory }
    private var hotelsAdapter: HotelsNavAdapter? = null

    fun setAdapter(adapter: HotelsNavAdapter) {
        hotelsAdapter = adapter
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = HotelsListBinding.inflate(inflater)
        binding.recyclerview.layoutManager = LinearLayoutManager(context)
        binding.recyclerview.adapter = HotelsAdapter(::onHotelItemClicked)
        lifecycleScope.launch {  
            viewModel.uiState.collect {
                if (it.isError) {
                    binding.errorText.isVisible = true
                    binding.recyclerview.isVisible = false
                } else {
                    binding.errorText.isVisible = false
                    binding.recyclerview.isVisible = true
                    (binding.recyclerview.adapter as HotelsAdapter).submitItems(it.hotels)
                }
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
        viewModel.getHotels()
    }

    private fun onHotelItemClicked(hotel: UiHotelItem) {
        hotelsAdapter?.createReservation(hotel)
//        Toast.makeText(context, "Clicked on ${hotel.name}", Toast.LENGTH_LONG).show()
    }
    
    companion object {
        fun newInstance(): HotelsFragment {
            return HotelsFragment()
        }
    }
}
