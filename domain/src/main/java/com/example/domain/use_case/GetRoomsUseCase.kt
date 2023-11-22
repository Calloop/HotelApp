package com.example.domain.use_case

import com.example.domain.common.Resource
import com.example.domain.entity.RoomsEntity
import com.example.domain.repository.HotelRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException
import javax.inject.Inject

class GetRoomsUseCase @Inject constructor(
    private val repository: HotelRepository
) {
    suspend operator fun invoke(): Flow<Resource<RoomsEntity>> = flow {
        try {
            emit(Resource.Loading())
            val rooms = repository.getRooms()
            emit(Resource.Success(rooms))
        } catch (e: IOException) {
            emit(Resource.Error("no connection"))
        }
    }.flowOn(Dispatchers.IO)
}