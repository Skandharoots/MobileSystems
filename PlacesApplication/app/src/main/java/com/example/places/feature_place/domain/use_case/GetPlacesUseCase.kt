package com.example.places.feature_place.domain.use_case

import com.example.places.feature_place.domain.model.Place
import com.example.places.feature_place.domain.repository.PlaceRepository
import kotlinx.coroutines.flow.Flow

class GetPlacesUseCase(
    private val repository: PlaceRepository
) {

    operator fun invoke(): Flow<List<Place>> {

        return repository.getPlaces()
    }
}