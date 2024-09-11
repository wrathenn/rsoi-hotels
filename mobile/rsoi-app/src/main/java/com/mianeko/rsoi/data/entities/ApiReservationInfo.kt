package com.mianeko.rsoi.data.entities

import com.mianeko.rsoi.domain.entities.PaymentInfo
import java.time.LocalDate
import java.util.UUID

data class ApiReservationInfo(
    val reservationUid: UUID,
    val hotelUid: UUID,
    val startDate: LocalDate?,
    val endDate: LocalDate?,
    val discount: Int,
    val status: ApiReservationStatus,

    val payment: PaymentInfo,
)
