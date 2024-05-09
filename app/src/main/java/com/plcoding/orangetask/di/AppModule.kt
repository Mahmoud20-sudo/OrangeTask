package com.plcoding.orangetask.di

import android.content.Context
import androidx.room.Room
import com.plcoding.orangetask.BuildConfig
import com.plcoding.orangetask.feature_movie.data.data_source.MovieDao
import com.plcoding.orangetask.feature_movie.data.data_source.MovieDatabase
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
    fun provideMovieDao(
        db: MovieDatabase
    ): MovieDao =
        db.movieDao()



    @ApplicationScope
    @Provides
    @Singleton
    fun provideApplicationScope(): CoroutineScope = CoroutineScope(SupervisorJob())

    @Retention(AnnotationRetention.RUNTIME)
    @Qualifier
    annotation class ApplicationScope
}