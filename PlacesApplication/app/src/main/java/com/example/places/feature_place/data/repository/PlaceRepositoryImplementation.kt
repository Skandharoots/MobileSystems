package com.example.places.feature_place.data.repository

import com.example.places.feature_place.data.data_source.PlaceDao
import com.example.places.feature_place.domain.model.Place
import com.example.places.feature_place.domain.repository.PlaceRepository
import kotlinx.coroutines.flow.Flow

class PlaceRepositoryImplementation(
    private val dao: PlaceDao
) : PlaceRepository {

    override fun getPlaces(): Flow<List<Place>> {
        return dao.getPlaces()
    }

    override suspend fun getPlaceById(id: Int): Place {
        return dao.getPlaceById(id)
    }

    override suspend fun insertPlace(place: Place) {
        dao.insertPlace(place)
    }

    override suspend fun deletePlace(place: Place) {
        dao.deletePlace(place)
    }
}