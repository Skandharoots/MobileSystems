package com.example.lab_application.data

import androidx.lifecycle.LiveData
import com.example.lab_application.model.Marker
import com.example.lab_application.model.Place
import java.util.Date

class PlaceRepository(private val placeDao: PlaceDao) {

    fun getAllPlaces() : LiveData<List<Place>> = placeDao.getPlaces()

    fun searchDatabaseByEvent(currentDate: Long) : LiveData<List<Place>> = placeDao.searchDatabaseByEvent(currentDate)

    fun searchDatabaseBetweenEvents(start: Long, end: Long) : LiveData<List<Place>> = placeDao.searchDatabaseBetweenEvents(start, end)

    suspend fun addPlace(place: Place) {
        placeDao.addPlace(place)
    }

    suspend fun updatePlace(place: Place) {
        placeDao.updatePlace(place)
    }

    suspend fun deletePlace(place: Place) {
        placeDao.deletePlace(place)
    }

}