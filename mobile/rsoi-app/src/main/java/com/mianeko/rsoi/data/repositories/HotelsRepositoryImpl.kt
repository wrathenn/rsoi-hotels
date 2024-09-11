package com.mianeko.rsoi.data.repositories

import android.content.SharedPreferences
import android.util.Log
import com.mianeko.rsoi.data.mappers.ApiHotelResponseToHotelsListMapper
import com.mianeko.rsoi.data.services.HotelApiService
import com.mianeko.rsoi.domain.entities.Hotel
import com.mianeko.rsoi.domain.repositories.HotelsRepository
import kotlinx.coroutines.CancellationException

internal class HotelsRepositoryImpl(
    private val sharedPreferences: SharedPreferences,
    private val apiHotelResponseToHotelsListMapper: ApiHotelResponseToHotelsListMapper,
    private val hotelApiService: HotelApiService,
): HotelsRepository {

    override suspend fun getHotels(page: Int, pageSize: Int): List<Hotel>? {
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
            val response = hotelApiService.getHotels(page, pageSize, header)
            if (!response.isSuccessful) {
                Log.e(TAG, "getHotels request failed: code = ${response.code()}, message = ${response.message()}")
            }
            response.body()?.let { apiHotelResponseToHotelsListMapper(it) }
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Log.e(TAG, "getHotels request failed.")
            null
        } catch (e: RuntimeException) {
            Log.e(TAG, "DEATH")
            null
        }
    }
    
    companion object {
        private const val TAG = "HotelsRepository"
    }
}
