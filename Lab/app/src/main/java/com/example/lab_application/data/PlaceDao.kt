package com.example.lab_application.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.lab_application.model.Place

@Dao
interface PlaceDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addPlace(place: Place)

    @Query("SELECT * FROM places_table ORDER BY date DESC")
    fun getPlaces() : LiveData<List<Place>>

    @Update
    suspend fun updatePlace(place: Place)

    @Delete
    suspend fun deletePlace(place: Place)

    @Query("SELECT * FROM places_table WHERE date > :currentDate")
    fun searchDatabaseByEvent(currentDate: Long) : LiveData<List<Place>>

    @Query("SELECT * FROM places_table WHERE date BETWEEN :start AND :end")
    fun searchDatabaseBetweenEvents(start: Long, end: Long) : LiveData<List<Place>>

}