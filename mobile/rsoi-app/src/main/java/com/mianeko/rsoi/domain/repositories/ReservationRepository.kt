package com.mianeko.rsoi.domain.repositories

import com.mianeko.rsoi.domain.entities.ReservationRequest
import com.mianeko.rsoi.domain.entities.ReservationResultInfo

interface ReservationRepository {
    suspend fun makeReservation(reservationRequest: ReservationRequest): ReservationResultInfo?
}
