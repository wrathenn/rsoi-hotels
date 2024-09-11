package com.mianeko.rsoi.data.repositories

import android.content.SharedPreferences
import android.util.Log
import com.mianeko.rsoi.data.services.ReservationApiService
import com.mianeko.rsoi.domain.entities.ReservationRequest
import com.mianeko.rsoi.domain.entities.ReservationResultInfo
import com.mianeko.rsoi.domain.repositories.ReservationRepository
import kotlinx.coroutines.CancellationException

class ReservationRepositoryImpl(
    private val sharedPreferences: SharedPreferences,
    private val reservationApiService: ReservationApiService,
): ReservationRepository {

    override suspend fun makeReservation(reservationRequest: ReservationRequest): ReservationResultInfo? {
        return try {
            val token = with(sharedPreferences) {
                getString("access_token", "")
            }
            if (token?.isEmpty() != false) {
                Log.e(TAG, "Failed to get access token")
                return null
            }
            Log.d(TAG, "Send request")
            val header = "Bearer $token"
            val response = reservationApiService.createReservation(header, reservationRequest)
            if (!response.isSuccessful) {
                Log.e(TAG, "make reservation request failed: code = ${response.code()}, message = ${response.message()}")
            }
            response.body()
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Log.e(TAG, "make reservation request failed.")
            null
        }
    }
    
    companion object {
        private const val TAG = "ReservationRepository"
    }
}
