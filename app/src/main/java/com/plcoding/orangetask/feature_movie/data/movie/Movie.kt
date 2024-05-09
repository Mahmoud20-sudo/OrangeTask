package com.plcoding.orangetask.feature_movie.data.model.movie

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "movies")
@Serializable
data class Movie(
    val cast: List<String>,
    val genres: List<String>,
    val rating: Int,
    val title: String,
    val year: Int,
    @PrimaryKey(autoGenerate = true) val id: Int? = null
)