package com.example.lab_application.data

import android.graphics.Bitmap
import android.graphics.BitmapFactory
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
    fun bitmapToBytArray(img: Bitmap): ByteArray {
        val outputStream = ByteArrayOutputStream()
        img.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        return outputStream.toByteArray()
    }

    @TypeConverter
    fun toBitmap(bytArray: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(bytArray, 0, bytArray.size)
    }
}
