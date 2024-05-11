package com.plcoding.orangetask.feature_movie.data.model.flickr

data class Photo(
    val farm: Int,
    val id: String,
    val isfamily: Int = 0,
    val isfriend: Int = 0,
    val ispublic: Int = 0,
    val owner: String = "",
    val secret: String,
    val server: String,
    val title: String = ""
){
    fun getImageUrl() = "https://farm$farm.staticflickr.com/$server/${id}_$secret.jpg"
}