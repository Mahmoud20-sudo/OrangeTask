package com.plcoding.orangetask.feature_movie.domain.use_case

import com.plcoding.orangetask.feature_movie.data.model.movie.Movie
import com.plcoding.orangetask.feature_movie.domain.repository.MovieRepository
import com.plcoding.orangetask.feature_movie.data.model.Resource
import kotlinx.coroutines.flow.Flow

class GetMovies(
    private val repository: MovieRepository
) {
    operator fun invoke(title: String): Flow<Resource<List<Movie>>> = repository.searchMovies(title)
}