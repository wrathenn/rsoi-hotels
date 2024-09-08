package com.mianeko.rsoi.ui.hotels

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mianeko.rsoi.databinding.HotelsItemBinding
import com.mianeko.rsoi.ui.entities.UiHotelItem

internal class HotelsAdapter(
    private val onItemClicked: (UiHotelItem) -> Unit, 
): RecyclerView.Adapter<HotelItemViewHolder>() {

    private var items: List<UiHotelItem> = emptyList()
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotelItemViewHolder {
        val binding = HotelsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HotelItemViewHolder(binding, onItemClicked)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: HotelItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun submitItems(hotels: List<UiHotelItem>) {
        items = hotels
        notifyItemRangeInserted(0, hotels.size)
    }
}
