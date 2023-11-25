package com.example.lab_application.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.lab_application.model.Marker
import com.example.lab_application.model.Place
import java.util.Date

@Dao
interface MarkerDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addMarker(marker: Marker)

    @Query("SELECT * FROM markers_table")
    fun getMarkers() : LiveData<List<Marker>>

    @Query("SELECT * FROM markers_table WHERE lat = :latitude AND lng = :longitude")
    fun getMarker(latitude: Double, longitude: Double) : LiveData<Marker>

    @Update
    suspend fun updateMarker(marker: Marker)

    @Delete
    suspend fun deleteMarker(marker: Marker)

    @Query("SELECT * FROM markers_table WHERE title LIKE :searchQuery")
    fun searchDatabaseByTitle(searchQuery: String) : LiveData<List<Marker>>

    @Query("SELECT * FROM markers_table WHERE about LIKE :searchQuery")
    fun searchDatabaseByDescription(searchQuery: String) : LiveData<List<Marker>>

    @Query("SELECT * FROM markers_table WHERE date / 1000 * 60 * 60 > :currentDate / 1000 * 60 * 60")
    fun searchDatabaseByEvent(currentDate: Date) : LiveData<List<Marker>>

}