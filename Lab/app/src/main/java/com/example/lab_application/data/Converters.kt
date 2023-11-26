package com.example.lab_application.data

import android.net.Uri
import androidx.room.TypeConverter
import java.util.Date

class Converters {

    @TypeConverter
    fun uriToString(uri: Uri): String? {
        return uri?.toString()
    }

    @TypeConverter
    fun stringToUri(uri: String): Uri? {
        return Uri.parse(uri)
    }
}
