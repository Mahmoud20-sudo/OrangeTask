package com.plcoding.orangetask.feature_movie.domain.use_case

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import com.plcoding.orangetask.MainCoroutineRule
import com.plcoding.orangetask.feature_movie.data.model.Resource
import com.plcoding.orangetask.feature_movie.data.model.flickr.ImagesResponse
import com.plcoding.orangetask.feature_movie.data.model.flickr.Photo
import com.plcoding.orangetask.feature_movie.data.model.flickr.Photos
import com.plcoding.orangetask.feature_movie.data.repository.PhotosFakeRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class GetPhotosTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var getPhotos: GetPhotos
    private lateinit var repository: PhotosFakeRepository

    @Before
    fun setup() {
        repository = PhotosFakeRepository()
        getPhotos = GetPhotos(repository)

        repository.setResponse(
            listOf(
                ImagesResponse(
                    stat = "ok",
                    photos = Photos(
                        page = 1,
                        perpage = 20,
                        pages = 3,
                        total = 60,
                        photo = listOf(
                            Photo(
                                farm = 66,
                                id = "53710356557",
                                secret = "7f5736f3de",
                                server = "65535"
                            ),
                            Photo(
                                farm = 66,
                                id = "53711476923",
                                secret = "7c871401d1",
                                server = "65535"
                            )
                        )
                    )
                ),
                ImagesResponse(
                    stat = "ok",
                    photos = Photos(
                        page = 2,
                        perpage = 20,
                        pages = 3,
                        total = 60,
                        photo = listOf(
                            Photo(
                                farm = 66,
                                id = "53710356532",
                                secret = "0b466026cf",
                                server = "65535"
                            ),
                            Photo(
                                farm = 66,
                                id = "53710356537",
                                secret = "7f89c82f86",
                                server = "65535"
                            )
                        )
                    )
                ),
                ImagesResponse(
                    stat = "fail",
                )
            )
        )
    }

    @Test
    fun `search existing page and title, returns photos`() = runTest {
        repository.setStat("ok")
        val result = getPhotos.invoke("17 Again", 1)
        assertThat(result.data?.data).isNotEmpty()
    }

    @Test
    fun `search non-existing page and title, returns photos`() = runTest {
        repository.setStat("ok")
        val result = getPhotos.invoke("17 Again", 4)
        val errorFlag = result is Resource.Error
        assertThat(errorFlag).isTrue()
    }

    @Test
    fun `search non-ok state, returns error`() = runTest {
        repository.setStat("fail")
        val result = getPhotos.invoke("17 Again", 1)
        val errorFlag = result is Resource.Error
        assertThat(errorFlag).isTrue()
    }

}