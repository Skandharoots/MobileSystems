package com.example.places.feature_place.domain.repository

import com.example.places.feature_place.domain.model.Place
import kotlinx.coroutines.flow.Flow

interface PlaceRepository {

    fun getPlaces(): Flow<List<Place>>

    suspend fun getPlaceById(id: Int): Place

    suspend fun insertPlace(place: Place)

    suspend fun deletePlace(place: Place)
}