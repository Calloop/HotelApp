package com.example.domain.entity

data class RoomsEntity(
    val roomEntities: List<RoomEntity>
)

data class RoomEntity(
    val id: Int,
    val imageUrls: List<String>,
    val name: String,
    val peculiarities: List<String>,
    val price: String,
    val pricePer: String
)