package com.example.places.feature_place.presentation.places

import com.example.places.feature_place.domain.model.Place

sealed class PlacesEvent {

    data class DeletePlace(val place: Place): PlacesEvent()
    object RestorePlace: PlacesEvent()
}
