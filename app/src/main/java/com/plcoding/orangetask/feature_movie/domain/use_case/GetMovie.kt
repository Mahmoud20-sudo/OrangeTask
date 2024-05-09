package com.plcoding.orangetask.feature_movie.domain.use_case

import com.plcoding.orangetask.feature_movie.data.model.movie.Movie
import com.plcoding.orangetask.feature_movie.domain.repository.MovieRepository

class GetMovie(
    private val repository: MovieRepository
) {

    suspend operator fun invoke(id: Int): Movie? {
        return repository.getMovieById(id)
    }
}