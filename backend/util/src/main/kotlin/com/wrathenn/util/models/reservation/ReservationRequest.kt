package com.wrathenn.util.models.reservation

import java.time.LocalDate
import java.util.*

data class ReservationRequest (
    val hotelUid: UUID,
    val startDate: LocalDate,
    val endDate: LocalDate,
)
