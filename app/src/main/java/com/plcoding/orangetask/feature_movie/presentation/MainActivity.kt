package com.plcoding.orangetask.feature_movie.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.plcoding.orangetask.feature_movie.presentation.details.MovieScreen
import com.plcoding.orangetask.feature_movie.presentation.movies.MoviesScreen
import com.plcoding.orangetask.feature_movie.presentation.util.Screen
import com.plcoding.orangetask.ui.theme.CleanArchitectureNoteAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            CleanArchitectureNoteAppTheme {

                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = Screen.MoviesScreen.route
                ) {
                    composable(route = Screen.MoviesScreen.route) {
                        MoviesScreen(navController = navController)
                    }
                    composable(
                        route = Screen.MovieDetailScreen.route +
                                "?movieTitle={movieTitle}",
                        arguments = listOf(
                            navArgument(
                                name = "movieTitle"
                            ) {
                                type = NavType.StringType
                                defaultValue = ""
                            }
                        )
                    ) {
                        MovieScreen(
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}