package com.plcoding.orangetask.feature_movie.data.api

import com.plcoding.orangetask.BuildConfig
import com.plcoding.orangetask.feature_movie.data.model.flickr.ImagesResponse
import com.plcoding.orangetask.feature_movie.data.model.Resource
import com.plcoding.orangetask.feature_movie.data.util.Constants.FLICKER_FORMAT
import com.plcoding.orangetask.feature_movie.data.util.Constants.FLICKER_METHOD
import kotlinx.coroutines.flow.Flow
import okhttp3.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("/services/rest/?")
    suspend fun getImages(
        @Query("text") text: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 20,
        @Query("method") method: String = FLICKER_METHOD,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("format") format: String = FLICKER_FORMAT,
        @Query("nojsoncallback") nojsoncallback: Int = 1
    ): ImagesResponse
}