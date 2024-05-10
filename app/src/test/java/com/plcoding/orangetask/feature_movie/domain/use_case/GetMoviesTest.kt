package com.plcoding.orangetask.feature_movie.domain.use_case

import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.plcoding.orangetask.MainCoroutineRule
import com.plcoding.orangetask.feature_movie.data.model.movie.Movie
import com.plcoding.orangetask.feature_movie.data.repository.MoviesFakeRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
@SmallTest
class GetMoviesTest {

    private lateinit var getMovies: GetMovies
    private lateinit var repository: MoviesFakeRepository

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        repository = MoviesFakeRepository()
        getMovies = GetMovies(repository)
        (1..3).forEach { i ->
            val movie = Movie(
                title = "Title$i",
                year = i,
                rating = i,
                cast = listOf("Actor$i"),
                genres = listOf("Action$i")
            )
            repository.insertMovie(movie)
        }
    }

    @Test
    fun `search existing movie, returns data`() = runTest {
        val result = getMovies.invoke("Title1")
        assertThat(result).isNotNull()
        assertThat(result.first().data).isNotEmpty()
    }

    @Test
    fun `search non-existing movie, returns exception`() = runTest {
        val result = getMovies.invoke("NUll")
        assertThat(result.first().data).isEmpty()
    }

    @Test
    fun `search empty title, returns all data`() = runTest {
        val result = getMovies.invoke("")
        assertThat(result.first().data).isNotEmpty()
    }
}