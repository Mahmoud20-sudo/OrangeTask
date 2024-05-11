package com.plcoding.orangetask.feature_movie.presentation.details

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.test.filters.MediumTest
import com.plcoding.orangetask.feature_movie.presentation.MainActivity
import com.plcoding.orangetask.feature_movie.presentation.movies.MoviesScreen
import com.plcoding.orangetask.feature_movie.presentation.util.clearAndSetContent
import com.plcoding.orangetask.feature_movie.presentation.util.Screen
import com.plcoding.orangetask.feature_movie.presentation.util.waitUntilTimeout
import com.plcoding.orangetask.feature_movie.util.Constants.PHOTOS_LIST_TAG
import com.plcoding.orangetask.feature_movie.util.Constants.TITLE_TAG
import com.plcoding.orangetask.ui.theme.CleanArchitectureNoteAppTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@MediumTest
class MovieScreenTest {

    @get:Rule(order = 1)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        hiltRule.inject()

        composeRule.clearAndSetContent {
            CleanArchitectureNoteAppTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = Screen.MovieDetailScreen.route
                ) {

                    composable(
                        route = Screen.MovieDetailScreen.route +
                                "?movieTitle=Summer" ,
                        arguments = listOf(
                            navArgument(
                                name = "movieTitle"
                            ) {
                                type = NavType.StringType
                                defaultValue = "Summer"
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
    @Test
    fun testPhotosListIsDisplayed() {
        // WAIT UNTIL PHOTOS DISPLAYED
        composeRule.waitUntilTimeout(2000L)
        composeRule.onNodeWithTag(PHOTOS_LIST_TAG).assertIsDisplayed()
    }

    @Test
    fun testTitleIsDisplayed() {
        composeRule.waitUntilTimeout(500L)
        composeRule.onNodeWithTag(TITLE_TAG).assertIsDisplayed()
    }

    @Test
    fun testPhotosListIsExists() {
        composeRule.waitUntilTimeout(500L)
        composeRule.onNodeWithTag(PHOTOS_LIST_TAG).assertExists()
    }
}