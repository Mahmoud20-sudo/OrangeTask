package com.plcoding.orangetask.feature_movie.data.repository

import com.plcoding.orangetask.feature_movie.data.model.Resource
import com.plcoding.orangetask.feature_movie.data.model.movie.Movie
import com.plcoding.orangetask.feature_movie.domain.repository.MovieRepository
import com.plcoding.orangetask.feature_movie.util.filterIfNotEmpty
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class MoviesFakeRepository : MovieRepository {
    private val movies = mutableListOf<Movie>()
    override fun searchMovies(title: String): Flow<Resource<List<Movie>>> =
        flowOf(Resource.Success(movies.filterIfNotEmpty(title) { value, element ->
            element.title.contains(
                value,
                ignoreCase = true
            )
        }))

    override suspend fun getMovieById(id: Int): Movie = movies.first { it.id == id }

    fun insertMovie(movie: Movie) {
        movies.add(movie)
    }
}