package com.example.lab_assignment.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.lab_assignment.model.Place

@Dao
interface PlaceDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addPlace(place: Place)

    @Query("SELECT * FROM places_table ORDER BY id ASC")
    fun getPlaces() : LiveData<List<Place>>

    //@Query("SELECT * FROM places_table WHERE places_table.id=:id")
    //fun getPlaceById(id: Int)

}