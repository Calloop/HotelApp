package com.example.hotelapp.model

import com.example.domain.entity.BookingDataEntity

data class BookingDataListState(
    val isLoading: Boolean = false,
    val bookingData: BookingDataEntity? = null,
    val error: String = ""
)