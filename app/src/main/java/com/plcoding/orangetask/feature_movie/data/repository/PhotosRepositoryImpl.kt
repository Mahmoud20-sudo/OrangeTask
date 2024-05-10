package com.plcoding.orangetask.feature_movie.data.repository

import com.plcoding.orangetask.feature_movie.data.api.Api
import com.plcoding.orangetask.feature_movie.data.model.PagedResponse
import com.plcoding.orangetask.feature_movie.data.model.Resource
import com.plcoding.orangetask.feature_movie.data.model.flickr.Photo
import com.plcoding.orangetask.feature_movie.data.util.Constants.FLICKER_OK_STAT
import com.plcoding.orangetask.feature_movie.domain.repository.PhotosRepository

class PhotosRepositoryImpl(
    private val api: Api
) : PhotosRepository {

    override suspend fun searchPhotos(title: String, page: Int): Resource<PagedResponse<Photo>> {
        return try {

            val result = api.getImages(text = title, page = page)

            if (result.stat == FLICKER_OK_STAT)
                Resource.Success(
                    PagedResponse(
                        data = result.photos.photo,
                        total = result.photos.photo.size,
                        page = page
                    )
                )
            else Resource.Error(Throwable(result.stat))
        } catch (e: Throwable) {
            Resource.Error(e)
        }
    }
}