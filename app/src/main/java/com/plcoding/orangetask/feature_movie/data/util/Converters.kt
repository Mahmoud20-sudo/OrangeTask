package com.plcoding.orangetask.feature_movie.data.util

import androidx.room.TypeConverter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class Converters {
    @TypeConverter
    fun convertListToJsonString(items: List<String>): String =
        Json.encodeToString(items)

    @TypeConverter
    fun convertJsonStringToList(json: String): List<String> =
        Json.decodeFromString(json)
}