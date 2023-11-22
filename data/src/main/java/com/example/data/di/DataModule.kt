package com.example.data.di

import com.example.data.api.HotelApi
import com.example.data.repository.HotelRepositoryImpl
import com.example.domain.common.Constants.BASE_URL
import com.example.domain.repository.HotelRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun providePaprikaApi(): HotelApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HotelApi::class.java)
    }

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    @Singleton
    fun provideHotelRepository(api: HotelApi): HotelRepository {
        return HotelRepositoryImpl(api)
    }
}