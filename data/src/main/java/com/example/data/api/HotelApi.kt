package com.example.data.api

import com.example.data.model.BookingDataModel
import com.example.data.model.HotelModel
import com.example.data.model.RoomsModel
import retrofit2.http.GET

interface HotelApi {

    @GET("v3/d144777c-a67f-4e35-867a-cacc3b827473")
    suspend fun getHotel(): HotelModel

    @GET("v3/8b532701-709e-4194-a41c-1a903af00195")
    suspend fun getRooms(): RoomsModel

    @GET("v3/63866c74-d593-432c-af8e-f279d1a8d2ff")
    suspend fun getBookingData(): BookingDataModel
}