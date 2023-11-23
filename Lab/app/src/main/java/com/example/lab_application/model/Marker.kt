package com.example.lab_application.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
@Entity(tableName = "markers_table")
data class Marker(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val date: Date,
    val about: String,
    val lat: Double,
    val lng: Double
) : Parcelable