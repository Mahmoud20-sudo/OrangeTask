package com.plcoding.orangetask.feature_movie.data.repository

import com.plcoding.orangetask.feature_movie.data.data_source.MovieDao
import com.plcoding.orangetask.feature_movie.data.model.movie.Movie
import com.plcoding.orangetask.feature_movie.domain.repository.MovieRepository
import com.plcoding.orangetask.feature_movie.data.model.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest

class MovieRepositoryImpl(
    private val dao: MovieDao
) : MovieRepository {
    override fun searchMovies(title: String): Flow<Resource<List<Movie>>> = channelFlow {
        send(Resource.Loading())
        val flowListMovies = dao.searchMovies(title)
        flowListMovies.collectLatest {
            send(Resource.Success(it))
        }
    }

    override suspend fun getMovieById(id: Int): Movie = dao.getMovieById(id)
}