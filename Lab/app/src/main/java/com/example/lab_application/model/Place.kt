package com.example.lab_application.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Blob
import java.util.Date

@Entity(tableName = "places_table")
data class Place(
    @PrimaryKey
    val id: Int,
    val city: String,
    val date: Date,
    val about: String,
    val image: Blob
    )
