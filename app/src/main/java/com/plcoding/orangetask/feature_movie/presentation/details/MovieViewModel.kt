package com.plcoding.orangetask.feature_movie.presentation.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.plcoding.orangetask.feature_movie.data.model.flickr.Photo
import com.plcoding.orangetask.feature_movie.data.model.movie.Movie
import com.plcoding.orangetask.feature_movie.domain.use_case.MovieUseCases
import com.plcoding.orangetask.feature_movie.domain.use_case.PhotosUseCases
import com.plcoding.orangetask.feature_movie.domain.util.PhotosPagingSource
import com.plcoding.orangetask.feature_movie.presentation.model.UiEvent
import com.plcoding.orangetask.feature_movie.util.Constants.DELAY_FILTER_QUERY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieUseCases: MovieUseCases,
    private val photosUseCases: PhotosUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _movie = MutableStateFlow<Movie?>(null)
    val movie = _movie.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _search = MutableStateFlow("")
    val search = _search.asStateFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = "",
        )

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val photosData: Flow<PagingData<Photo>> =
        search.debounce(DELAY_FILTER_QUERY.milliseconds).flatMapLatest { query ->
            Pager(
                config = PagingConfig(pageSize = 20),
                initialKey = 1,
                pagingSourceFactory = {
                    PhotosPagingSource(
                        photosUseCases.getPhotos,
                        search = query
                    )
                }
            ).flow.cachedIn(viewModelScope)
        }

    init {
        savedStateHandle.get<String>("movieTitle")?.let { movieTitle ->
            if (movieTitle.isNotBlank()) {
                _search.value = movieTitle
                viewModelScope.launch {
                    movieUseCases.getMovie(movieTitle)?.also { movie ->
                        _movie.value = movie
                    }
                }
            }
        }
    }
}