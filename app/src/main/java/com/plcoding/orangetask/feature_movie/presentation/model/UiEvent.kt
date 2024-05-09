package com.plcoding.orangetask.feature_movie.presentation.model

sealed class UiEvent {
    data class ShowSnackbar(val message: String): UiEvent()
}