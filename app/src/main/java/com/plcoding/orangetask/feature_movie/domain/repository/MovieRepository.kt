package com.plcoding.orangetask.feature_movie.domain.repository

import com.plcoding.orangetask.feature_movie.data.model.movie.Movie
import com.plcoding.orangetask.feature_movie.data.model.Resource
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun searchMovies(title: String): Flow<Resource<List<Movie>>>

    suspend fun getMovieById(id: Int): Movie?
}