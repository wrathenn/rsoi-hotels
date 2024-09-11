package com.mianeko.rsoi.di

import android.content.Context
import android.content.SharedPreferences
import com.mianeko.rsoi.data.mappers.ApiHotelResponseToHotelsListMapper
import com.mianeko.rsoi.data.repositories.AuthorizationRepository
import com.mianeko.rsoi.data.repositories.HotelsRepositoryImpl
import com.mianeko.rsoi.data.repositories.ReservationRepositoryImpl
import com.mianeko.rsoi.domain.hotels.GetHotelsUseCaseImpl
import com.mianeko.rsoi.domain.repositories.HotelsRepository
import com.mianeko.rsoi.domain.repositories.ReservationRepository
import com.mianeko.rsoi.ui.book.viewModels.BookViewModelFactory
import com.mianeko.rsoi.ui.hotels.viewModels.HotelsViewModelFactory
import com.mianeko.rsoi.ui.mappers.HotelsListToUiHotelItemsListMapper
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.bind
import org.koin.dsl.module

val domainModule = module {

    single {
        HotelsRepositoryImpl(get(), get(), get())
    } bind HotelsRepository::class

    single { 
        ApiHotelResponseToHotelsListMapper()
    } bind ApiHotelResponseToHotelsListMapper::class
    
    single { 
        GetHotelsUseCaseImpl(get())
    } bind GetHotelsUseCaseImpl::class
    
    single { 
        HotelsListToUiHotelItemsListMapper()
    }
    
    single {
        HotelsViewModelFactory(get(), get())
    }

    single {
        BookViewModelFactory(get())
    }
    
    single { 
        ReservationRepositoryImpl(get(), get())
    } bind ReservationRepository::class
    
    single {
        androidContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    } bind SharedPreferences::class
    
    single { 
        AuthorizationRepository()
    }
}
