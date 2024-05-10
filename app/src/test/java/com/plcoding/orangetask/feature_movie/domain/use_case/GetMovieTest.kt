package com.plcoding.orangetask.feature_movie.domain.use_case

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.plcoding.orangetask.MainCoroutineRule
import com.plcoding.orangetask.feature_movie.data.model.movie.Movie
import com.plcoding.orangetask.feature_movie.data.repository.MoviesFakeRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@SmallTest
class GetMovieTest{
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var getMovie: GetMovie
    private lateinit var repository: MoviesFakeRepository

    @Before
    fun setUp(){
        repository = MoviesFakeRepository()
        getMovie = GetMovie(repository)
        (1..3).forEach { i ->
            val movie = Movie(
                title = "Title$i",
                year = i,
                rating = i,
                cast = listOf("Actor$i"),
                genres = listOf("Action$i"),
                id = i
            )
            repository.insertMovie(movie)
        }
    }

    @Test
    fun `search existing title, returns movie`() = runTest {
        val result = getMovie.invoke("Title1")
        assertThat(result).isNotNull()
    }

    @Test(expected = RuntimeException::class)
    fun `search non-existing title, returns null`() = runTest {
        val result = getMovie.invoke("Non Existing")
        assertThat(result).isNull()
    }
}