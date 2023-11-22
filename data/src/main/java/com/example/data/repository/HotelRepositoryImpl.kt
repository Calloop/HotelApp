package com.example.data.repository

import com.example.data.api.HotelApi
import com.example.data.extensions.toDomain
import com.example.domain.entity.BookingDataEntity
import com.example.domain.entity.HotelEntity
import com.example.domain.entity.RoomsEntity
import com.example.domain.repository.HotelRepository
import javax.inject.Inject

class HotelRepositoryImpl @Inject constructor(
    private val api: HotelApi
) : HotelRepository {

    override suspend fun getHotel(): HotelEntity {
        return api.getHotel().toDomain()
    }

    override suspend fun getRooms(): RoomsEntity {
        return api.getRooms().toDomain()
    }

    override suspend fun getBookingData(): BookingDataEntity {
        return api.getBookingData().toDomain()
    }
}