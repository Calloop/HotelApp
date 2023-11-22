package com.example.domain.repository

import com.example.domain.entity.BookingDataEntity
import com.example.domain.entity.HotelEntity
import com.example.domain.entity.RoomsEntity

interface HotelRepository {
    suspend fun getHotel(): HotelEntity
    suspend fun getRooms(): RoomsEntity
    suspend fun getBookingData(): BookingDataEntity
}