package com.plcoding.orangetask.feature_movie.presentation.movies.data.data_source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.plcoding.orangetask.MainCoroutineRule
import com.plcoding.orangetask.di.AppModule
import com.plcoding.orangetask.feature_movie.data.data_source.MovieDao
import com.plcoding.orangetask.feature_movie.data.data_source.MovieDatabase
import com.plcoding.orangetask.feature_movie.data.model.movie.Movie
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@SmallTest
@HiltAndroidTest
class MovieDaoTest {

    @get:Rule
    var hiltInject = HiltAndroidRule(this)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Inject
    @Named("test_db")
    lateinit var movieDatabase: MovieDatabase

    private lateinit var movieDao: MovieDao

    @Before
    fun setup() {
        hiltInject.inject()
        movieDao = movieDatabase.movieDao()
    }

    @After
    fun teardown() {
        movieDatabase.close()
    }

    @Test
    fun insertMoviesList() = runTest {
        val movie1 = Movie(
            title = "Test Title1",
            rating = 1,
            year = 2022,
            genres = listOf("Action1"),
            cast = listOf("Test Cast1"),
            id = 1
        )
        val movie2 = Movie(
            title = "Test Title2",
            rating = 1,
            year = 2000,
            genres = listOf("Action2"),
            cast = listOf("Test Cast2"),
            id = 2
        )
        val movie3 = Movie(
            title = "Test Title3",
            rating = 5,
            year = 1988,
            genres = listOf("Action3"),
            cast = listOf("Test Cast3"),
            id = 3
        )

        val moviesList = listOf(movie1, movie2, movie3)
        movieDao.insertMovie(moviesList)

        val result = movieDao.searchMovies("")
        assertThat(result.first()).contains(movie1)
    }

    @Test
    fun searchMoviesListReturnsResult() = runTest {
        val movie1 = Movie(
            title = "Test Title1",
            rating = 1,
            year = 2022,
            genres = listOf("Action1"),
            cast = listOf("Test Cast1"),
            id = 1
        )
        val movie2 = Movie(
            title = "Test Title2",
            rating = 1,
            year = 2000,
            genres = listOf("Action2"),
            cast = listOf("Test Cast2"),
            id = 2
        )
        val movie3 = Movie(
            title = "Test Title3",
            rating = 5,
            year = 1988,
            genres = listOf("Action3"),
            cast = listOf("Test Cast3"),
            id = 3
        )

        val moviesList = listOf(movie1, movie2, movie3)
        movieDao.insertMovie(moviesList)

        val result = movieDao.searchMovies("Title1")
        assertThat(result.first()[0].title).contains("Title1")
    }

    @Test
    fun searchMoviesListReturnsEmpty() = runTest {
        val movie1 = Movie(
            title = "Test Title1",
            rating = 1,
            year = 2022,
            genres = listOf("Action1"),
            cast = listOf("Test Cast1"),
            id = 1
        )
        val movie2 = Movie(
            title = "Test Title2",
            rating = 1,
            year = 2000,
            genres = listOf("Action2"),
            cast = listOf("Test Cast2"),
            id = 2
        )
        val movie3 = Movie(
            title = "Test Title3",
            rating = 5,
            year = 1988,
            genres = listOf("Action3"),
            cast = listOf("Test Cast3"),
            id = 3
        )

        val moviesList = listOf(movie1, movie2, movie3)
        movieDao.insertMovie(moviesList)

        val result = movieDao.searchMovies("Empty")
        assertThat(result.first()).isEmpty()
    }

    @Test
    fun getMovieByExistingId() = runTest {
        val movie1 = Movie(
            title = "Test Title1",
            rating = 1,
            year = 2022,
            genres = listOf("Action1"),
            cast = listOf("Test Cast1"),
            id = 1
        )

        val moviesList = listOf(movie1)
        movieDao.insertMovie(moviesList)

        val result = movieDao.getMovieByTitle("Test Title1")
        assertThat(result).isEqualTo(movie1)
    }

    @Test
    fun getMovieByNonExistingId() = runTest {
        val movie1 = Movie(
            title = "Test Title1",
            rating = 1,
            year = 2022,
            genres = listOf("Action1"),
            cast = listOf("Test Cast1"),
            id = 1
        )

        val moviesList = listOf(movie1)
        movieDao.insertMovie(moviesList)

        val result = movieDao.getMovieByTitle("Non Existing")
        assertThat(result).isNull()
    }

}