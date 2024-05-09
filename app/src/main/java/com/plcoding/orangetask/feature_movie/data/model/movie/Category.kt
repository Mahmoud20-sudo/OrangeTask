package com.plcoding.orangetask.feature_movie.data.model.movie

data class Category(
    val name: String,//used for categorization by year
    val movies: List<Movie>
)