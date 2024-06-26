package com.example.lab_application.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.lab_application.model.Marker
import com.example.lab_application.model.Place
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

@Database(entities = [Place::class, Marker::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class PlaceDatabase : RoomDatabase() {

    abstract fun placeDao() : PlaceDao

    abstract fun markerDao() : MarkerDao

    companion object {
        @Volatile
        private var INSTANCE: PlaceDatabase? = null


        @OptIn(InternalCoroutinesApi::class)
        fun getDatabase(context: Context): PlaceDatabase {

            val tempInstance = INSTANCE
            if(tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PlaceDatabase::class.java,
                    "place_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}