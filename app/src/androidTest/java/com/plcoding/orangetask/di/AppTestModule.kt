package com.plcoding.orangetask.di

import android.content.Context
import androidx.paging.PagingSource
import androidx.room.Room
import com.plcoding.orangetask.BuildConfig
import com.plcoding.orangetask.feature_movie.data.api.Api
import com.plcoding.orangetask.feature_movie.data.data_source.MovieDao
import com.plcoding.orangetask.feature_movie.data.data_source.MovieDatabase
import com.plcoding.orangetask.feature_movie.data.model.flickr.Photo
import com.plcoding.orangetask.feature_movie.data.repository.MovieRepositoryImpl
import com.plcoding.orangetask.feature_movie.data.repository.PhotosRepositoryImpl
import com.plcoding.orangetask.feature_movie.domain.repository.MovieRepository
import com.plcoding.orangetask.feature_movie.domain.repository.PhotosRepository
import com.plcoding.orangetask.feature_movie.domain.use_case.*
import com.plcoding.orangetask.feature_movie.domain.util.PhotosPagingSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppTestModule {

    @Provides
    @Singleton
    @Named("test_db")
    fun provideMovieDatabase(
        @ApplicationContext context: Context,
    ): MovieDatabase {
        return Room.databaseBuilder(
            context, MovieDatabase::class.java, MovieDatabase.DATABASE_NAME
        ).allowMainThreadQueries().build()
    }

    @ApplicationScope
    @Provides
    @Singleton
    fun provideApplicationScope(): CoroutineScope = CoroutineScope(SupervisorJob())

    @Retention(AnnotationRetention.RUNTIME)
    @Qualifier
    annotation class ApplicationScope

}