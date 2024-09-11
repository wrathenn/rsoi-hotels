package com.mianeko.rsoi.ui.reservations

import android.graphics.Color
import android.util.Log
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.mianeko.rsoi.databinding.ReservationItemBinding
import com.mianeko.rsoi.domain.entities.ReservationInfo
import com.mianeko.rsoi.domain.entities.ReservationStatus.CANCELED
import com.mianeko.rsoi.domain.entities.ReservationStatus.PAID

class ReservationItemViewHolder(
    private val reservationsItemBinding: ReservationItemBinding,
    private val onCancelReservationClicked: (ReservationInfo) -> Unit,
): RecyclerView.ViewHolder(reservationsItemBinding.root) {
    private var item: ReservationInfo? = null

    init {
        reservationsItemBinding.cancelButton.setOnClickListener { _ ->
            item?.let {
                Log.d(TAG, "Item clicked for $it")
                onCancelReservationClicked(it)
            }
        }
    }

    fun bind(item: ReservationInfo) {
        this.item = item
        with(reservationsItemBinding) {
            when (item.status) {
                PAID -> {
                    val color = Color.parseColor(ENABLED_COLOR)
                    root.isEnabled = true
                    startDate.text = "С ${item.startDate}"
                    startDate.setTextColor(color)
                    endDate.text = "По ${item.endDate}"
                    endDate.setTextColor(color)
                    hotelName.text = item.hotel.name
                    hotelName.setTextColor(color)
                    hotelLocation.text = item.hotel.fullAddress
                    hotelLocation.setTextColor(color)
                    hotelRating.text = item.hotel.stars.toString()
                    hotelRating.setTextColor(color)
                    hotelPrice.text = String.format("%d р", item.payment.price)
                    hotelPrice.setTextColor(color)

                    cancelButton.isVisible = true
                }
                CANCELED -> {
                    val color = Color.parseColor(DISABLED_COLOR)
                    root.isEnabled = false
                    startDate.text = "С ${item.startDate}"
                    startDate.setTextColor(color)
                    endDate.text = "По ${item.endDate}"
                    endDate.setTextColor(color)
                    hotelName.text = item.hotel.name
                    hotelName.setTextColor(color)
                    hotelLocation.text = item.hotel.fullAddress
                    hotelLocation.setTextColor(color)
                    hotelRating.text = item.hotel.stars.toString()
                    hotelRating.setTextColor(color)
                    hotelPrice.text = String.format("%d р", item.payment.price)
                    hotelPrice.setTextColor(color)
                    
                    cancelButton.isVisible = false
                }
            }
        }
    }

    companion object {
        private const val TAG = "HotelItemViewHolder"
        private const val DISABLED_COLOR = "#49454F"
        private const val ENABLED_COLOR = "#1D1B20"
    }
}
