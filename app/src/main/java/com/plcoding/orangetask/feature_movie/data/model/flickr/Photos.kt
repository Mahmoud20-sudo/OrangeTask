package com.plcoding.orangetask.feature_movie.data.model.flickr

data class Photos(
    val page: Int,
    val pages: Int,
    val perpage: Int,
    val photo: List<Photo>,
    val total: Int
)