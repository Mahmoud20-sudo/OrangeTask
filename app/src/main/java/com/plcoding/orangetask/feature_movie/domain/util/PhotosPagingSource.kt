package com.plcoding.orangetask.feature_movie.domain.util

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.plcoding.orangetask.feature_movie.data.model.PagedResponse
import com.plcoding.orangetask.feature_movie.data.model.flickr.Photo
import com.plcoding.orangetask.feature_movie.domain.repository.PhotosRepository
import com.plcoding.orangetask.feature_movie.domain.use_case.GetPhotos

class PhotosPagingSource(
    private val getPhotos: GetPhotos,
    val search: String,
) : PagingSource<Int, Photo>() {
    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        return try {
            val currentPageNumber = params.key ?: INITIAL_LOAD_SIZE

            val result = getPhotos.invoke(
                page = currentPageNumber,
                title = search
            )

            val photos: PagedResponse<Photo> = result.data as PagedResponse<Photo>

            val nextKey = if (photos.data?.isEmpty() == true) {
                null
            } else {
                currentPageNumber + (params.loadSize / PAGE_SIZE)
            }

            return LoadResult.Page(
                prevKey = null,
                nextKey = nextKey,
                data = photos.data ?: listOf()
            )
        } catch (e: Throwable) {
            LoadResult.Error(e)
        }
    }

    companion object {
        const val PAGE_SIZE = 10
        private const val INITIAL_LOAD_SIZE = 0
    }
}