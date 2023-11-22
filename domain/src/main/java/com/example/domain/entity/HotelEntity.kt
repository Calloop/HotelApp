package com.example.domain.entity

data class HotelEntity(
    val aboutTheHotelEntity: AboutTheHotelEntity,
    val address: String,
    val id: Int,
    val imageUrls: List<String>,
    val minimalPrice: Int,
    val name: String,
    val priceForIt: String,
    val rating: Int,
    val ratingName: String
)