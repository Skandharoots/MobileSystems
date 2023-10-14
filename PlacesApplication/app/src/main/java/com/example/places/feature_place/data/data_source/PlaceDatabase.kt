package com.example.places.feature_place.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.places.feature_place.domain.model.Place

@Database(
    entities = [Place::class],
    version = 1
)
abstract class PlaceDatabase : RoomDatabase() {

    abstract val placeDao: PlaceDao

    companion object {
        const val DATABASE_NAME = "places_db"
    }
}