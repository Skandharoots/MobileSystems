package com.example.places.feature_place.domain.use_case

data class PlaceUseCases(
    val getPlaces: GetPlacesUseCase,
    val deletePlace: DeletePlaceUseCase,
    val addPlace: AddPlaceUseCase
)
