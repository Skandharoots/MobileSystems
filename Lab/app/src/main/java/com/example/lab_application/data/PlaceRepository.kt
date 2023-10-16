package com.example.lab_application.data

import androidx.lifecycle.LiveData
import com.example.lab_application.model.Place

class PlaceRepository(private val placeDao: PlaceDao) {

    fun getAllPlaces() : LiveData<List<Place>> = placeDao.getPlaces()

    //suspend fun addPlace(place: Place) {
     //   placeDao.addPlace(place)
    //}

    //fun getPlaceById(id: Int) {
     //   placeDao.getPlaceById(id)
    //}
}