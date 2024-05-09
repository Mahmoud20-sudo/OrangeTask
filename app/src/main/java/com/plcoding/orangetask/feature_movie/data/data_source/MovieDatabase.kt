package com.plcoding.orangetask.feature_movie.data.data_source

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.plcoding.orangetask.R
import com.plcoding.orangetask.di.AppModule
import com.plcoding.orangetask.feature_movie.data.model.movie.Movie
import com.plcoding.orangetask.feature_movie.data.util.Constants.MOVIES_JSON_KEY
import com.plcoding.orangetask.feature_movie.data.util.Converters
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Provider

@Database(
    entities = [Movie::class], version = 1, exportSchema = false
)
@TypeConverters(Converters::class)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {
        const val DATABASE_NAME = "movies_db"
    }

    class PrepopulateCallback @Inject constructor(
        @AppModule.ApplicationScope private val scope: CoroutineScope,
        @ApplicationContext private val context: Context,
        private val movieDao: Provider<MovieDao>,
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            scope.launch(Dispatchers.IO) {
                val jsonText =
                    context.resources.openRawResource(R.raw.movies).bufferedReader().use {
                            val text = it.readText()
                            val jsonObject = JSONObject(text)
                            jsonObject.getJSONArray(MOVIES_JSON_KEY).toString()
                        }
                val moviesList = Json.decodeFromString<List<Movie>>(jsonText)
                movieDao.get().insertMovie(moviesList)
            }
        }
    }
}