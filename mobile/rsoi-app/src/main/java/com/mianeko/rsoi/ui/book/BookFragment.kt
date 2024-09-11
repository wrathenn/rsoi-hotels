package com.mianeko.rsoi.ui.book

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.mianeko.rsoi.R
import com.mianeko.rsoi.databinding.BookFragmentBinding
import com.mianeko.rsoi.databinding.NavBarLayoutBinding
import com.mianeko.rsoi.ui.book.entities.ReservationUiState
import com.mianeko.rsoi.ui.book.viewModels.BookViewModel
import com.mianeko.rsoi.ui.book.viewModels.BookViewModelFactory
import com.mianeko.rsoi.ui.entities.UiHotelItem
import com.mianeko.rsoi.ui.navigation.HotelsNavAdapter
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class BookFragment: Fragment(R.layout.book_fragment) {

    private var _binding: BookFragmentBinding? = null
    private val binding get() = _binding!!
    private var _navBinding: NavBarLayoutBinding? = null
    private val navBinding get() = _navBinding!!

    private var hotelsAdapter: HotelsNavAdapter? = null

    private val dateFormat by lazy { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()) }
    private val bookViewModelFactory: BookViewModelFactory by inject()
    private val viewModel: BookViewModel by viewModels { bookViewModelFactory }

    fun setAdapter(adapter: HotelsNavAdapter) {
        hotelsAdapter = adapter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BookFragmentBinding.inflate(inflater, container, false)
        _navBinding = NavBarLayoutBinding.bind(binding.root)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val hotel = arguments?.getParcelable(BOOK_ARGS) as? UiHotelItem ?: run {
            Toast.makeText(context, "Что-то пошло не так, нельзя забронировать отель", Toast.LENGTH_LONG).show()
            hotelsAdapter?.showHotels()
            return
        }
        viewModel.setHotel(hotel = hotel)

        val calendar = Calendar.getInstance()
        
        navBinding.hotelsNavButton.setOnClickListener { 
            hotelsAdapter?.showHotels()
        }
        navBinding.accountButton.setOnClickListener { 
            hotelsAdapter?.authorize()
        }
        navBinding.loyaltyNavButton.setOnClickListener { 
            hotelsAdapter?.showLoyaltyInfo()
        }
        navBinding.reservationsNavButton.setOnClickListener { 
            hotelsAdapter?.showReservations()
        }

        binding.startDateValue.setOnClickListener {
            val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val startTime = dateFormat.format(calendar.time)
                val endDate = viewModel.uiState.value.endDate
                if (endDate != null && endDate < startTime) {
                    Toast.makeText(context, "Дата выселения должна быть позже даты заселения", Toast.LENGTH_LONG).show()
                    return@OnDateSetListener
                }
                binding.startDateValue.text = startTime
                viewModel.setStartDate(startTime)
            }

            DatePickerDialog(requireContext(), dateSetListener,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        binding.endDateValue.setOnClickListener {
            val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val endTime = dateFormat.format(calendar.time)
                val startDate = viewModel.uiState.value.startDate
                if (startDate != null && endTime < startDate) {
                    Toast.makeText(context, "Дата выселения должна быть позже даты заселения", Toast.LENGTH_LONG).show()
                    return@OnDateSetListener
                }
                binding.endDateValue.text = endTime
                viewModel.setEndDate(endTime)
            }

            DatePickerDialog(requireContext(), dateSetListener,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        binding.bookBtn.setOnClickListener { 
            if (viewModel.uiState.value.isFullInfo) {
                viewModel.reserve()
            } else {
                Toast.makeText(context, "Сначала выберите даты заселения и выселения", Toast.LENGTH_SHORT).show()
            }
        }
        lifecycleScope.launch {
            viewModel.uiState.collect(::renderState)
        }
    }
    
    private fun renderState(reservationUiState: ReservationUiState) {
        Log.d(TAG, "New state $reservationUiState")
        binding.bookBtn.isEnabled = reservationUiState.isFullInfo
        binding.progress.isVisible = reservationUiState.isInProgress
        binding.reservationInfo.isVisible = reservationUiState.reservationResultInfo != null
        if (reservationUiState.isError) {
            Toast.makeText(context, "Не удалось забронировать отель", Toast.LENGTH_LONG).show()
        }
        if (reservationUiState.reservationResultInfo != null) {
            binding.reservationInfo.text = "Отель успешно забронирован! Ваша скидка составила " +
                        "${reservationUiState.reservationResultInfo.discount}%. Итоговая стоимость с учетом скидки " +
                        "составила ${reservationUiState.reservationResultInfo.payment.price} рублей"
        }
//        if (reservationUiState.isFullInfo) {
//            binding.costValue.text = 
//        }
        if (reservationUiState.hotel != null) {
            with(binding) {
                name.text = reservationUiState.hotel.name
                addressValue.text = String.format("%s, %s, %s", reservationUiState.hotel.country, reservationUiState.hotel.city, reservationUiState.hotel.address)
                priceValue.text = String.format("%d р/сутки", reservationUiState.hotel.price)
                starsValue.text = String.format("%d звезд", reservationUiState.hotel.rating)
            }
        }
    }
    
    companion object {
        private const val TAG = "BookFragment"
        private const val BOOK_ARGS = "BOOK_ARGUMENTS"
        
        internal fun newInstance(hotel: UiHotelItem): BookFragment {
            return BookFragment().apply {
                arguments = bundleOf(
                    BOOK_ARGS to hotel
                )
            }
        }
    }
}
