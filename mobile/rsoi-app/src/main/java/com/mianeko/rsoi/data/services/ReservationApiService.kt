package com.mianeko.rsoi.data.services

import com.mianeko.rsoi.domain.entities.ReservationInfo
import com.mianeko.rsoi.domain.entities.ReservationRequest
import com.mianeko.rsoi.domain.entities.ReservationResultInfo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.UUID

interface ReservationApiService {

    @GET("api/gateway/reservations")
    suspend fun getReservations(
        @Header("Authorization") token: String,
    ): Response<List<ReservationInfo>>
    
    @POST("api/gateway/reservations")
    suspend fun createReservation(
        @Header("Authorization") token: String,
        @Body reservation: ReservationRequest,
    ): Response<ReservationResultInfo>

    @DELETE("api/gateway/reservations/{reservationUid}")
    suspend fun deleteReservation(
        @Header("Authorization") token: String,
        @Path("reservationUid") reservationUid: UUID,
    ): Response<Unit>
}
