package com.example.lab_application.data

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream
import java.util.Date

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }

    @TypeConverter
    fun uriToString(uri: Uri): String? {
        return uri?.toString()
    }

    @TypeConverter
    fun stringToUri(uri: String): Uri? {
        return Uri.parse(uri)
    }
}
