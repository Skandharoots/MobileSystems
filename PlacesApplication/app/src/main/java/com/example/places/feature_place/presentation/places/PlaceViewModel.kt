package com.example.places.feature_place.presentation.places

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.places.feature_place.domain.model.Place
import com.example.places.feature_place.domain.use_case.PlaceUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlacesViewModel @Inject constructor(
    private val placeUseCase: PlaceUseCases
) : ViewModel() {

    private var recentlyDeletedPlace: Place? = null;

    private var getPlacesJob: Job? = null

    init {
        getPlaces()
    }
    fun onEvent(event: PlacesEvent) {
        when(event) {
            is PlacesEvent.DeletePlace -> {
                viewModelScope.launch {
                    placeUseCase.deletePlace(event.place)
                    recentlyDeletedPlace = event.place
                }
            }
            is PlacesEvent.RestorePlace -> {
                viewModelScope.launch {
                    placeUseCase.addPlace(recentlyDeletedPlace ?: return@launch)
                    recentlyDeletedPlace = null
                }
            }
        }
    }

    private fun getPlaces() {
        getPlacesJob?.cancel()
        placeUseCase.getPlaces().launchIn(viewModelScope)
    }

}
