package com.plcoding.orangetask.di

import android.content.Context
import androidx.room.Room
import com.plcoding.orangetask.BuildConfig
import com.plcoding.orangetask.feature_movie.data.api.Api
import com.plcoding.orangetask.feature_movie.data.data_source.MovieDao
import com.plcoding.orangetask.feature_movie.data.data_source.MovieDatabase
import com.plcoding.orangetask.feature_movie.data.repository.MovieRepositoryImpl
import com.plcoding.orangetask.feature_movie.data.repository.PhotosRepositoryImpl
import com.plcoding.orangetask.feature_movie.domain.repository.MovieRepository
import com.plcoding.orangetask.feature_movie.domain.repository.PhotosRepository
import com.plcoding.orangetask.feature_movie.domain.use_case.GetMovie
import com.plcoding.orangetask.feature_movie.domain.use_case.GetMovies
import com.plcoding.orangetask.feature_movie.domain.use_case.GetPhotos
import com.plcoding.orangetask.feature_movie.domain.use_case.MovieUseCases
import com.plcoding.orangetask.feature_movie.domain.use_case.PhotosUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()

    @Provides
    @Singleton
    fun provideApiInterface(retrofit: Retrofit): Api = retrofit.create(Api::class.java)


    @Provides
    @Singleton
    fun provideMovieDatabase(
        @ApplicationContext context: Context,
        roomCallback: MovieDatabase.PrepopulateCallback,
    ): MovieDatabase {
        return Room.databaseBuilder(
            context, MovieDatabase::class.java, MovieDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration()
            .addCallback(roomCallback)
            .build()
    }

    @Provides
    @Singleton
    fun provideMovieRepository(movieDao: MovieDao): MovieRepository {
        return MovieRepositoryImpl(movieDao)
    }

    @Provides
    fun provideMovieDao(
        db: MovieDatabase
    ): MovieDao =
        db.movieDao()

    @Provides
    @Singleton
    fun provideMovieUseCases(repository: MovieRepository): MovieUseCases {
        return MovieUseCases(
            getMovies = GetMovies(repository),
            getMovie = GetMovie(repository)
        )
    }

    @Provides
    @Singleton
    fun providePhotosRepository(
        api: Api
    ): PhotosRepository =
        PhotosRepositoryImpl(api = api)

    @Provides
    @Singleton
    fun providePhotosUseCases(repository: PhotosRepository): PhotosUseCases {
        return PhotosUseCases(
            getPhotos = GetPhotos(repository)
        )
    }


    @ApplicationScope
    @Provides
    @Singleton
    fun provideApplicationScope(): CoroutineScope = CoroutineScope(SupervisorJob())

    @Retention(AnnotationRetention.RUNTIME)
    @Qualifier
    annotation class ApplicationScope
}