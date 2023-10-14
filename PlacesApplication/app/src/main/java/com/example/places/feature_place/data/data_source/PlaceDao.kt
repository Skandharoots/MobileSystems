package com.example.places.feature_place.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.places.feature_place.domain.model.Place
import java.util.concurrent.Flow

@Dao
interface PlaceDao {

    @Query("SELECT * FROM place")
    fun getPlaces(): kotlinx.coroutines.flow.Flow<List<Place>>

    @Query("SELECT * FROM place WHERE id = :id")
    suspend fun getPlaceById(id: Int): Place

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlace(place: Place)

    @Delete
    suspend fun deletePlace(place: Place)
}