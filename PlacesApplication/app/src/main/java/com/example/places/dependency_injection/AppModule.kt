package com.example.places.dependency_injection

import android.app.Application
import androidx.room.Room
import com.example.places.feature_place.data.data_source.PlaceDao
import com.example.places.feature_place.data.data_source.PlaceDatabase
import com.example.places.feature_place.data.repository.PlaceRepositoryImplementation
import com.example.places.feature_place.domain.repository.PlaceRepository
import com.example.places.feature_place.domain.use_case.AddPlaceUseCase
import com.example.places.feature_place.domain.use_case.DeletePlaceUseCase
import com.example.places.feature_place.domain.use_case.GetPlacesUseCase
import com.example.places.feature_place.domain.use_case.PlaceUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePlaceDatabase(app: Application) : PlaceDatabase {

        return Room.databaseBuilder(
            app,
            PlaceDatabase::class.java,
            PlaceDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun providePlaceRepository(db: PlaceDatabase) : PlaceRepository {
        return PlaceRepositoryImplementation(db.placeDao)
    }

    @Provides
    @Singleton
    fun providePlaceUseCases(repository: PlaceRepository) : PlaceUseCases {
        return PlaceUseCases(
            getPlaces = GetPlacesUseCase(repository),
            deletePlace = DeletePlaceUseCase(repository),
            addPlace = AddPlaceUseCase(repository)
        )
    }
}