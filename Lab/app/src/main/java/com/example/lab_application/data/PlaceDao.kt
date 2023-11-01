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
    fun addPlace(place: Place)

    @Query("SELECT * FROM places_table ORDER BY date DESC")
    fun getPlaces() : LiveData<List<Place>>

    @Update
    fun updatePlace(place: Place)

    @Delete
    fun deletePlace(place: Place)


}