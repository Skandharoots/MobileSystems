package com.example.lab_application.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.lab_application.data.MarkerRepository
import com.example.lab_application.data.PlaceDatabase
import com.example.lab_application.model.Marker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date

class MarkerViewModel(application: Application) : AndroidViewModel(application) {

    val readAllData: LiveData<List<Marker>>
    private val repository: MarkerRepository

    init {
        val markerDao = PlaceDatabase.getDatabase(application).markerDao()
        repository = MarkerRepository(markerDao)
        readAllData = repository.getAllMarkers()
    }

    fun getMarker(latitude: Double, longitude: Double): LiveData<Marker> {
        return repository.getMarker(latitude, longitude)
    }

    fun searchDatabaseByTitle(searchQuery : String) : LiveData<List<Marker>> {
        return repository.searchDatabaseByTitle(searchQuery)
    }

    fun searchDatabaseByDescription(searchQuery : String) : LiveData<List<Marker>> {
        return repository.searchDatabaseByDescription(searchQuery)
    }

    fun addMarker(marker: Marker) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addMarker(marker)
        }
    }

    fun updateMarker(marker: Marker) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateMarker(marker)
        }
    }

    fun deleteMarker(marker: Marker) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteMarker(marker)
        }
    }
}