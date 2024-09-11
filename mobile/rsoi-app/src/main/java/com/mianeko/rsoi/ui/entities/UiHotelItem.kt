package com.mianeko.rsoi.ui.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class UiHotelItem(
    val uuid: UUID,
    val name: String,
    val city: String,
    val country: String,
    val address: String,
    val rating: Int,
    val price: Int,
): Parcelable
