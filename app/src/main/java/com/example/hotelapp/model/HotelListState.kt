package com.example.hotelapp.model

import com.example.domain.entity.HotelEntity

data class HotelListState(
    val isLoading: Boolean = false,
    val hotelEntity: HotelEntity? = null,
    val error: String = ""
)