package com.plcoding.orangetask.feature_movie.data.repository

import android.media.Image
import com.plcoding.orangetask.feature_movie.data.model.PagedResponse
import com.plcoding.orangetask.feature_movie.data.model.Resource
import com.plcoding.orangetask.feature_movie.data.model.flickr.ImagesResponse
import com.plcoding.orangetask.feature_movie.data.model.flickr.Photo
import com.plcoding.orangetask.feature_movie.data.model.flickr.Photos
import com.plcoding.orangetask.feature_movie.data.model.movie.Movie
import com.plcoding.orangetask.feature_movie.data.util.Constants
import com.plcoding.orangetask.feature_movie.domain.repository.MovieRepository
import com.plcoding.orangetask.feature_movie.domain.repository.PhotosRepository
import com.plcoding.orangetask.feature_movie.util.filterIfNotEmpty
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class PhotosFakeRepository : PhotosRepository {
    private var stat = ""
    private var response: MutableList<ImagesResponse> = mutableListOf()

    fun setStat(value: String) {
        stat = value
    }

    fun setResponse(value: List<ImagesResponse>) {
        response.addAll(value)
    }

    override suspend fun searchPhotos(title: String, page: Int): Resource<PagedResponse<Photo>> {

        return try {
            val result = kotlin.runCatching { response.first { it.photos?.page == page } }
                .getOrElse {
                    ImagesResponse()
                }

            if (result.photos != null && stat == Constants.FLICKER_OK_STAT)
                Resource.Success(
                    PagedResponse(
                        data = result.photos?.photo,
                        total = result.photos?.photo?.size,
                        page = page
                    )
                )
            else Resource.Error(Throwable(result.stat))
        } catch (e: Throwable) {
            Resource.Error(e)
        }
    }
}