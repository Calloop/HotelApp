package com.example.data.extensions

import com.example.data.model.AboutTheHotelModel
import com.example.data.model.BookingDataModel
import com.example.data.model.HotelModel
import com.example.data.model.RoomModel
import com.example.data.model.RoomsModel
import com.example.domain.entity.AboutTheHotelEntity
import com.example.domain.entity.BookingDataEntity
import com.example.domain.entity.HotelEntity
import com.example.domain.entity.RoomEntity
import com.example.domain.entity.RoomsEntity

fun HotelModel.toDomain(): HotelEntity {
    return HotelEntity(
        aboutTheHotelModel.toDomain(),
        address,
        id,
        imageUrls,
        minimalPrice,
        name,
        priceForIt,
        rating,
        ratingName
    )
}

fun AboutTheHotelModel.toDomain(): AboutTheHotelEntity {
    return AboutTheHotelEntity(
        description, peculiarities
    )
}

fun RoomModel.toDomain(): RoomEntity {
    return RoomEntity(
        id, imageUrls, name, peculiarities, price.toString(), pricePer
    )
}

fun RoomsModel.toDomain(): RoomsEntity {
    return RoomsEntity(roomModels.map { it.toDomain() }
    )
}

fun BookingDataModel.toDomain(): BookingDataEntity {
//    val touristModel = TouristModel(1, null, null, null, null, null, null)
//    val touristModelList = listOf(touristModel.toDomain())

    val detailsKeys = listOf(
        "Вылет из",
        "Страна, Город",
        "Даты",
        "Кол-во ночей",
        "Отель",
        "Номер",
        "Питание"
    )
    val priceKeys =
        listOf("Тур", "Топливный сбор", "Сервисный сбор", "К оплате")

    val sumBookingPrice = buildString {
        append((tourPrice + fuelCharge + serviceCharge).toString())
        append(" ₽")
    }
    val buttonText = buildString {
        append("Оплатить ")
        append((tourPrice + fuelCharge + serviceCharge).toString())
        append(" ₽")
    }
    val rating = buildString {
        append(rating)
        append(" ")
        append(ratingName)
    }
    val tourDate = buildString {
        append(tourDateStart)
        append(" – ")
        append(tourDateStop)
    }
    val numberOfNights = buildString {
        append(numberOfNights)
        append(" ночей")
    }
    val tourPrice = buildString {
        append(tourPrice)
        append(" ₽")
    }
    val fuelCharge = buildString {
        append(fuelCharge)
        append(" ₽")
    }
    val serviceCharge = buildString {
        append(serviceCharge)
        append(" ₽")
    }
    val bookingDetails = listOf(
        departure, arrivalCountry, tourDate, hotelName, numberOfNights, room, nutrition
    )
    val bookingPrice = listOf(
        tourPrice,
        fuelCharge,
        serviceCharge,
        sumBookingPrice
    )

    return BookingDataEntity(
        rating,
        hotelAddress,
        hotelName,
        id,
        detailsKeys,
        priceKeys,
        bookingDetails,
        bookingPrice,
        buttonText
    )
}