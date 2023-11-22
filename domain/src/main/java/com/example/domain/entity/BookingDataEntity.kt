package com.example.domain.entity

data class BookingDataEntity(
    val rating: String,
    val hotelAddress: String,
    val hotelName: String,
    val id: Int,
    val bookingDetailsKeys: List<String>,
    val bookingPriceKeys: List<String>,
    val bookingDetailsValues: List<String>,
    val bookingPriceValues: List<String>,
    val buttonText: String
)