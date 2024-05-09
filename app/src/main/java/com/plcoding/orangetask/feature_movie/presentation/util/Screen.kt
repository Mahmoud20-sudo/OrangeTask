package com.plcoding.orangetask.feature_movie.presentation.util

sealed class Screen(val route: String) {
    data object MoviesScreen: Screen("movies_screen")
    data object MovieDetailScreen: Screen("movie_details_screen")
}
