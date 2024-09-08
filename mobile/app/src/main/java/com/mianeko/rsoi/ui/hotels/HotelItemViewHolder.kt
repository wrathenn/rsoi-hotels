package com.mianeko.rsoi.ui.hotels

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.mianeko.rsoi.databinding.HotelsItemBinding
import com.mianeko.rsoi.ui.entities.UiHotelItem

internal class HotelItemViewHolder(
    private val hotelsItemBinding: HotelsItemBinding,
    private val onItemClick: (UiHotelItem) -> Unit,
): RecyclerView.ViewHolder(hotelsItemBinding.root) {

    private var item: UiHotelItem? = null

    init {
        hotelsItemBinding.bookButton.setOnClickListener { _ ->
            item?.let {
                Log.d(TAG, "Item clicked for $it")    
                onItemClick(it) 
            }
        }
    }

    fun bind(item: UiHotelItem) {
        this.item = item
        with(hotelsItemBinding) {
            hotelName.text = item.name
            hotelLocation.text = String.format("%s, %s", item.city, item.country)
            hotelRating.text = item.rating.toString()
            hotelPrice.text = String.format("%d р/сут", item.price)
        }
    }
    
    companion object {
        private const val TAG = "HotelItemViewHolder"
    }
}
