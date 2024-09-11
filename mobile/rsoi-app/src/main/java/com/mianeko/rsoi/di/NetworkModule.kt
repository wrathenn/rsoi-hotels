package com.mianeko.rsoi.di

import com.mianeko.rsoi.data.services.AuthorizationService
import com.mianeko.rsoi.data.services.HotelApiService
import com.mianeko.rsoi.data.services.LoyaltyApiService
import com.mianeko.rsoi.data.services.ReservationApiService
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {

    single {
        Retrofit.Builder()
            .baseUrl("http://hotels.rsoi.wrathen.ru/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    } bind Retrofit::class

    fun createHotelsApiService(retrofit: Retrofit): HotelApiService {
        return retrofit.create(HotelApiService::class.java)
    }
    
    fun createAuthApiService(retrofit: Retrofit): AuthorizationService {
        return retrofit.create(AuthorizationService::class.java)
    }
    
    fun createReservationApiService(retrofit: Retrofit): ReservationApiService {
        return retrofit.create(ReservationApiService::class.java)
    }
    
    fun createLoyaltyApiService(retrofit: Retrofit): LoyaltyApiService {
        return retrofit.create(LoyaltyApiService::class.java)
    }

    single {
        createHotelsApiService(get())
    }
    
    single { 
        createAuthApiService(get())
    }
    
    single { 
        createReservationApiService(get())
    }
    
    single {
        createLoyaltyApiService(get())
    }
}
