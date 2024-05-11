package com.plcoding.orangetask.feature_movie.presentation.movies

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.test.espresso.Espresso
import androidx.test.filters.LargeTest
import com.plcoding.orangetask.feature_movie.presentation.MainActivity
import com.plcoding.orangetask.feature_movie.presentation.details.MovieScreen
import com.plcoding.orangetask.feature_movie.presentation.util.Screen
import com.plcoding.orangetask.feature_movie.presentation.util.clearAndSetContent
import com.plcoding.orangetask.feature_movie.presentation.util.waitUntilTimeout
import com.plcoding.orangetask.feature_movie.util.Constants.MOVIES_LIST_TAG
import com.plcoding.orangetask.feature_movie.util.Constants.PHOTOS_LIST_TAG
import com.plcoding.orangetask.feature_movie.util.Constants.TITLE_TAG
import com.plcoding.orangetask.ui.theme.CleanArchitectureNoteAppTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@LargeTest
class MoviesEndToEndTest {

    @get:Rule(order = 1)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    var composeRule = createAndroidComposeRule<MainActivity>()

    @OptIn(ExperimentalAnimationApi::class)
    @Before
    fun setup() {
        hiltRule.inject()
        composeRule.clearAndSetContent {
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
                                "?movieId={movieId}",
                        arguments = listOf(
                            navArgument(
                                name = "movieId"
                            ) {
                                type = NavType.IntType
                                defaultValue = -1
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
    fun clickOnListItemOpenDetailScreen() {
        // WAIT TILL MOVIES LIST LOADED
        composeRule.waitUntilTimeout(2000L)

        // CLICK ON MOVIES FIRST ITEM
        composeRule.onNodeWithTag(MOVIES_LIST_TAG)
            .onChildren()
            .onFirst()
            .performClick()

        // DETAILS SCREEN OPENS
        composeRule.onNodeWithTag(TITLE_TAG).assertIsDisplayed()

        // WAIT UNTIL PHOTOS LIST LOADED
        composeRule.waitUntilTimeout(2000L)
        composeRule.onNodeWithTag(PHOTOS_LIST_TAG).assertIsDisplayed()
    }

    @Test
    fun clickOnBackButtonOpenMoviesScreen() {
        // WAIT TILL MOVIES LIST LOADED
        composeRule.waitUntilTimeout(2000L)

        // CLICK ON MOVIES FIRST ITEM
        composeRule.onNodeWithTag(MOVIES_LIST_TAG)
            .onChildren()
            .onFirst()
            .performClick()

        // DETAILS SCREEN OPENS
        composeRule.onNodeWithTag(TITLE_TAG).assertIsDisplayed()

        // CLICK ON BACK BUTTON
        Espresso.pressBack()

        // BACK TO MOVIES SCREEN
        composeRule.onNodeWithTag(MOVIES_LIST_TAG).assertIsDisplayed()
    }

}