package com.mianeko.rsoi.domain.entities

import java.util.UUID

data class ReservationRequest(
    val hotelUid: UUID,
    val startDate: String,
    val endDate: String,
)
