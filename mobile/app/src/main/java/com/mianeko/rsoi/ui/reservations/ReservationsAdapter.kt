package com.mianeko.rsoi.ui.reservations

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mianeko.rsoi.databinding.ReservationItemBinding
import com.mianeko.rsoi.domain.entities.ReservationInfo
import com.mianeko.rsoi.domain.entities.ReservationStatus

internal class ReservationsAdapter(
    private val onCancelReservationClicked: (ReservationInfo) -> Unit
): RecyclerView.Adapter<ReservationItemViewHolder>() {
    private var items: List<ReservationInfo> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationItemViewHolder {
        val binding = ReservationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReservationItemViewHolder(binding, onCancelReservationClicked)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ReservationItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun submitItems(reservations: List<ReservationInfo>) {
        items = reservations
        notifyItemRangeInserted(0, reservations.size)
    }
    
    fun changeItemState(reservationInfo: ReservationInfo, newStatus: ReservationStatus) {
        val newList = items.toMutableList()
        var pos = newList.indexOf(reservationInfo)
        newList.remove(reservationInfo)
        notifyItemRemoved(pos)
        newList.add(reservationInfo.copy(
            status = newStatus
        ))
        pos = newList.indexOf(reservationInfo)
        notifyItemInserted(pos)
    }
}
