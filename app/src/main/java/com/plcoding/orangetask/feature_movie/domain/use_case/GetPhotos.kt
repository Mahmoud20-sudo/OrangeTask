package com.plcoding.orangetask.feature_movie.domain.use_case

import com.plcoding.orangetask.feature_movie.domain.repository.PhotosRepository

class GetPhotos(
    private val repository: PhotosRepository
) {
    suspend operator fun invoke(title: String, page: Int) = repository.searchPhotos(title, page)
}