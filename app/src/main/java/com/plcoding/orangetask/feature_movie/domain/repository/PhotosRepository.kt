package com.plcoding.orangetask.feature_movie.domain.repository

import com.plcoding.orangetask.feature_movie.data.model.PagedResponse
import com.plcoding.orangetask.feature_movie.data.model.Resource
import com.plcoding.orangetask.feature_movie.data.model.flickr.Photo

interface PhotosRepository {
    suspend fun searchPhotos(title: String, page: Int): Resource<PagedResponse<Photo>>
}