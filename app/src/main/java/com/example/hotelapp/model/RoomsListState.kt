package com.example.hotelapp.model

import com.example.domain.entity.RoomsEntity

data class RoomsListState(
    val isLoading: Boolean = false,
    val rooms: RoomsEntity? = null,
    val error: String = ""
)