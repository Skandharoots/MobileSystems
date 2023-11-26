package com.example.lab_application.model

import android.net.Uri
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "places_table")
data class Place(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val city: String,
    val date: Long,
    val about: String,
    val rating: Int,
    val image: Uri
    ) : Parcelable
