package com.plcoding.orangetask.feature_movie.data.model

sealed class Resource<T> (
    val data: T? = null,
    val error: Throwable? = null,
)  {
    class Success<T>(data: T) : Resource<T>(data)
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Error<T>(throwable: Throwable?, data: T? = null) : Resource<T>(data, throwable)
}
