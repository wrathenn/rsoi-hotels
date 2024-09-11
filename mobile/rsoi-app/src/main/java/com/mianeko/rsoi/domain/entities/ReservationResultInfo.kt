package com.mianeko.rsoi.domain.entities

import java.util.UUID

data class ReservationResultInfo(
    val reservationUid: UUID,
    val hotelUid: UUID,
    val startDate: String,
    val endDate: String,
    val discount: Int,
    val status: ReservationStatus,
    val payment: PaymentInfo
)

data class ReservationInfo(
    val reservationUid: UUID,

    val hotel: HotelShort,

    val startDate: String?,
    val endDate: String?,
    val status: ReservationStatus,

    val payment: PaymentInfo,
)
