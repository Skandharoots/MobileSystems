package com.example.lab_application.adapter

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.lab_application.data.PlaceDatabase
import com.example.lab_application.data.PlaceRepository
import com.example.lab_application.model.Place
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlaceViewModel(application : Application) : AndroidViewModel(application) {

    private val readAllData: LiveData<List<Place>>
    private val repository: PlaceRepository

    init {
        val placeDao = PlaceDatabase.getDatabase(application).placeDao()
        repository = PlaceRepository(placeDao)
        readAllData = repository.getAllPlaces()
    }

    fun addPlace(place: Place) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addPlace(place)
        }
    }

}