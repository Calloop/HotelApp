package com.example.domain.use_case

import com.example.domain.common.Resource
import com.example.domain.entity.BookingDataEntity
import com.example.domain.repository.HotelRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException
import javax.inject.Inject

class GetBookingDataUseCase @Inject constructor(
    private val repository: HotelRepository
) {
    suspend operator fun invoke(): Flow<Resource<BookingDataEntity>> = flow {
        try {
            emit(Resource.Loading())
            val bookingData = repository.getBookingData()
            emit(Resource.Success(bookingData))
        } catch (e: IOException) {
            emit(Resource.Error("no connection"))
        }
    }.flowOn(Dispatchers.IO)
}