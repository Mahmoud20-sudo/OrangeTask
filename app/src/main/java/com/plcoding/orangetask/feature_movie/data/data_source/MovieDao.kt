package com.plcoding.orangetask.feature_movie.data.data_source

import androidx.room.*
import com.plcoding.orangetask.feature_movie.data.model.movie.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movies: List<Movie>)

    @Query("SELECT * FROM movies WHERE title = :title")
    suspend fun getMovieByTitle(title: String): Movie

    @Query("SELECT * FROM movies WHERE title LIKE '%' || :title || '%'")
    fun searchMovies(title: String): Flow<List<Movie>>
}