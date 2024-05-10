package com.plcoding.orangetask.feature_movie.data.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.plcoding.orangetask.feature_movie.data.model.Resource
import com.plcoding.orangetask.feature_movie.data.model.flickr.ImagesResponse
import com.plcoding.orangetask.feature_movie.data.repository.PhotosRepositoryImpl
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@SmallTest
class ApiTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var server: MockWebServer

    private lateinit var api: Api

    @Before
    fun setup() {
        server = MockWebServer()
        api = Retrofit.Builder()
            .baseUrl(server.url("/"))//Pass any base url like this
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(Api::class.java)
    }

    @After
    fun tea() {
        server.shutdown()
    }

    @Test
    fun `get photos, returns Success`() = runTest {
        val dto = ImagesResponse()//The object I want back as response
        val gson: Gson = GsonBuilder().create()
        val json = gson.toJson(dto)!!//Convert the object into json string using GSON
        val res = MockResponse()//Make a fake response for our server call
        res.setBody(json)//set the body of the fake response as the json string you are expecting as a response
        server.enqueue(res)//add it in the server response queue

        val data = api.getImages("2012", 1)//make the call to our fake server(as we are using fake base url)
        server.takeRequest()//let the server take the request

        assertEquals(data, dto)//the data you are getting as the call response should be same
    }

    @Test
    fun `get photos, returns Error`() = runTest {
        //First step is to make the server ready with a response
        val res = MockResponse()
        res.setResponseCode(404)
        server.enqueue(res)

        //Second step is to create a call
        val repo = PhotosRepositoryImpl(api)
        val data = repo.searchPhotos("2012", 1)

        //Third step is to tell our server to accept the call created
        server.takeRequest()

        assert(data is Resource.Error)//Our repo shows error as the response code was 400.
    }
}