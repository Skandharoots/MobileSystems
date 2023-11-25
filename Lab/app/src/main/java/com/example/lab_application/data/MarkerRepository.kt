package com.example.lab_application.data

import androidx.lifecycle.LiveData
import com.example.lab_application.model.Marker
import java.util.Date

class MarkerRepository(private val markerDao: MarkerDao) {

    fun getAllMarkers() : LiveData<List<Marker>> = markerDao.getMarkers()

    fun getMarker(latitude: Double, longitude: Double) : LiveData<Marker> = markerDao.getMarker(latitude, longitude)

    fun searchDatabaseByTitle(searchQuery: String) : LiveData<List<Marker>> = markerDao.searchDatabaseByTitle(searchQuery)

    fun searchDatabaseByDescription(searchQuery: String) : LiveData<List<Marker>> = markerDao.searchDatabaseByDescription(searchQuery)

    suspend fun addMarker(marker: Marker) {
        markerDao.addMarker(marker)
    }

    suspend fun updateMarker(marker: Marker) {
        markerDao.updateMarker(marker)
    }

    suspend fun deleteMarker(marker: Marker) {
        markerDao.deleteMarker(marker)
    }


}