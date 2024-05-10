package com.plcoding.orangetask.feature_movie.presentation.movies

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.test.filters.MediumTest
import com.plcoding.orangetask.feature_movie.presentation.MainActivity
import com.plcoding.orangetask.feature_movie.presentation.util.clearAndSetContent
import com.plcoding.orangetask.feature_movie.presentation.util.Screen
import com.plcoding.orangetask.feature_movie.util.Constants.MOVIES_LIST_TAG
import com.plcoding.orangetask.feature_movie.util.Constants.SEARCH_TAG
import com.plcoding.orangetask.ui.theme.CleanArchitectureNoteAppTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalAnimationApi::class)
@HiltAndroidTest
@MediumTest
class MoviesScreenTest {

    @get:Rule(order = 1)
    val hiltRules = HiltAndroidRule(this)

    @get:Rule(order = 2)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        hiltRules.inject()

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
                }
            }
        }
    }

    @Test
    fun testSearchMovies() {
        composeRule.onNodeWithTag(MOVIES_LIST_TAG).assertExists()
        composeRule.onNodeWithTag(SEARCH_TAG).performTextInput("17 Again")
        composeRule.onNodeWithText("17 Again").assertIsDisplayed()
    }

    @Test
    fun testMoviesIsDisplayed() {
        composeRule.onNodeWithTag(MOVIES_LIST_TAG).assertExists()
    }

    @Test
    fun testSearchIsDisplayed() {
        composeRule.onNodeWithTag(SEARCH_TAG).assertExists()
    }
}