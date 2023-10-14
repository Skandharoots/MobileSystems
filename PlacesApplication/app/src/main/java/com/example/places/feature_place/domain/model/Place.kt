package com.example.places.feature_place.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.nio.file.Path
import java.util.Date

@Entity
data class Place(
    val city: String,
    val date: Date,
    val about: String,
    val imgSrc: String,
    val rating: Int,
    @PrimaryKey val id: Int? = null
) {
    companion object {
        val ratings = listOf(1, 2, 3 ,4, 5)
    }
}

class InvalidPlaceException(message: String) : Exception(message) {

}
