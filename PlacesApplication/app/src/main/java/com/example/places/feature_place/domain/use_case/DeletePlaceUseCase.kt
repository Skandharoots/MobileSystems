package com.example.places.feature_place.domain.use_case

import com.example.places.feature_place.domain.model.Place
import com.example.places.feature_place.domain.repository.PlaceRepository

class DeletePlaceUseCase(
    private val repository: PlaceRepository
) {

    suspend operator fun invoke(place: Place) {
        repository.deletePlace(place)
    }
}