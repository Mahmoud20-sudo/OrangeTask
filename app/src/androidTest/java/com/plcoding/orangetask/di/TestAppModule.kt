package com.plcoding.orangetask.di

import android.content.Context
import androidx.room.Room
import com.plcoding.orangetask.feature_movie.data.data_source.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Named
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

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