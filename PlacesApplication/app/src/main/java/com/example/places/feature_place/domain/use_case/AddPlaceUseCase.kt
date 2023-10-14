package com.example.places.feature_place.domain.use_case

import com.example.places.feature_place.domain.model.InvalidPlaceException
import com.example.places.feature_place.domain.model.Place
import com.example.places.feature_place.domain.repository.PlaceRepository

class AddPlaceUseCase(
    private val repository: PlaceRepository
) {

    suspend operator fun invoke(place: Place) {
        if(place.city.isBlank()) {
            throw InvalidPlaceException("The city name is empty.")
        }
        repository.insertPlace(place)
    }
}