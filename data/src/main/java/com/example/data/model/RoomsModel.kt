package com.example.data.model

import com.google.gson.annotations.SerializedName

data class RoomsModel(
    @SerializedName("rooms")
    val roomModels: List<RoomModel>
)

data class RoomModel(
    val id: Int,
    @SerializedName("image_urls")
    val imageUrls: List<String>,
    val name: String,
    val peculiarities: List<String>,
    val price: Int,
    @SerializedName("price_per")
    val pricePer: String
)