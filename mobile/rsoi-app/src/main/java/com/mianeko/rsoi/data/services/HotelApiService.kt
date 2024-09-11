package com.mianeko.rsoi.data.services

import com.mianeko.rsoi.data.entities.ApiHotelResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

internal interface HotelApiService {
    @GET("api/gateway/hotels")
    suspend fun getHotels(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Header("Authorization") authHeader: String,
    ): Response<ApiHotelResponse>
}
