package com.mianeko.rsoi.data.services

import com.mianeko.rsoi.data.entities.ApiLoyaltyInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

internal interface LoyaltyApiService {
    
    @GET("api/gateway/loyalty")
    suspend fun getLoyaltyInfo(
        @Header("Authorization") token: String
    ): Response<ApiLoyaltyInfo>
}
