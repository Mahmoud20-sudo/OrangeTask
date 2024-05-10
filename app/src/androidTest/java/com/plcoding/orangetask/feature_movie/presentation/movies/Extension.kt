package com.plcoding.orangetask.feature_movie.presentation.movies

import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.plcoding.orangetask.feature_movie.presentation.MainActivity

object Extension {
    fun AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>.clearAndSetContent(
        content: @Composable () -> Unit
    ) {
        (this.activity.findViewById<ViewGroup>(android.R.id.content)
            ?.getChildAt(0) as? ComposeView)?.setContent(content)
            ?: this.setContent(content)
    }
}