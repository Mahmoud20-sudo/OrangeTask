package com.plcoding.orangetask.feature_movie.presentation.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.orangetask.feature_movie.data.model.movie.Movie
import com.plcoding.orangetask.feature_movie.domain.use_case.MovieUseCases
import com.plcoding.orangetask.feature_movie.presentation.model.UiEvent
import com.plcoding.orangetask.feature_movie.data.model.Resource
import com.plcoding.orangetask.feature_movie.util.Constants.DELAY_FILTER_QUERY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val movieUseCases: MovieUseCases
) : ViewModel() {

    private val _movies = MutableStateFlow<List<Movie>>(listOf())
    val movies = _movies.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(500),
        _movies.value
    )

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun setSearchText(query: String) {
        _searchText.value = query
    }

    init {
        viewModelScope.launch {
            searchText.collectLatest {
                delay(DELAY_FILTER_QUERY)
                movieUseCases.getMovies(searchText.value).collectLatest { res ->
                    renderResults(res)
                }
            }
        }
    }

    private fun renderResults(res: Resource<List<Movie>>) {
        when (res) {
            is Resource.Error -> {
                //FAIL
                viewModelScope.launch {
                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(
                            message = res.error?.message ?: "Couldn't retrieve movies"
                        )
                    )
                }
            }

            is Resource.Loading -> {
                //LOADING
            }

            is Resource.Success -> {
                res.data?.let {
                    _movies.value = it
                }
            }
        }
    }
}