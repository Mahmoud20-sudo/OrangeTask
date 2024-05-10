package com.plcoding.orangetask.feature_movie.util

fun <T> List<T>.filterIfNotEmpty(value: String?, predicate: (String, T) -> Boolean): List<T> {
    return when {
        value.isNullOrBlank() -> this
        else -> filter { predicate(value, it) }
    }
}