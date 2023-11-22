package com.example.domain.use_case

import com.example.domain.common.Resource
import com.example.domain.entity.HotelEntity
import com.example.domain.repository.HotelRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException
import javax.inject.Inject

class GetHotelUseCase @Inject constructor(
    private val repository: HotelRepository
) {
    suspend operator fun invoke(): Flow<Resource<HotelEntity>> = flow {
        try {
            emit(Resource.Loading())
            val hotel = repository.getHotel()
            emit(Resource.Success(hotel))
        } catch (e: IOException) {
            emit(Resource.Error("no connection"))
        }
    }.flowOn(Dispatchers.IO)
}