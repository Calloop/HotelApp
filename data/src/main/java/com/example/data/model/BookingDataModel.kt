package com.example.data.model

import com.google.gson.annotations.SerializedName

data class BookingDataModel(
    @SerializedName("arrival_country")
    val arrivalCountry: String,
    val departure: String,
    @SerializedName("fuel_charge")
    val fuelCharge: Int,
    @SerializedName("horating")
    val rating: Int,
    @SerializedName("hotel_adress")
    val hotelAddress: String,
    @SerializedName("hotel_name")
    val hotelName: String,
    val id: Int,
    @SerializedName("number_of_nights")
    val numberOfNights: Int,
    val nutrition: String,
    @SerializedName("rating_name")
    val ratingName: String,
    val room: String,
    @SerializedName("service_charge")
    val serviceCharge: Int,
    @SerializedName("tour_date_start")
    val tourDateStart: String,
    @SerializedName("tour_date_stop")
    val tourDateStop: String,
    @SerializedName("tour_price")
    val tourPrice: Int
)