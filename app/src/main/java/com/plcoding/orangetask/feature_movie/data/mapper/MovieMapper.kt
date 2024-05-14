package com.plcoding.orangetask.feature_movie.data.mapper

import com.plcoding.orangetask.feature_movie.data.model.movie.Movie
import com.plcoding.orangetask.feature_movie.data.model.movie.Category

fun List<Movie>.categorize(): List<Category> {
    return groupBy({ it.year }, { it })
        .map {
            Category(
                name = it.key.toString(),
                movies = it.value.sortedByDescending { item -> item.rating }.take(5)
            )
        }
}