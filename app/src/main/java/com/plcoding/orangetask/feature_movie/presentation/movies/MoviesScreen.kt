package com.plcoding.orangetask.feature_movie.presentation.movies

import androidx.compose.animation.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.plcoding.orangetask.feature_movie.data.mapper.categorize
import com.plcoding.orangetask.feature_movie.data.model.movie.Movie
import com.plcoding.orangetask.feature_movie.presentation.model.UiEvent
import com.plcoding.orangetask.feature_movie.presentation.movies.components.BasicTextField
import com.plcoding.orangetask.feature_movie.presentation.util.Screen
import com.plcoding.orangetask.feature_movie.util.Constants
import com.plcoding.orangetask.feature_movie.util.Constants.SEARCH_TAG
import kotlinx.coroutines.flow.collectLatest

@ExperimentalAnimationApi
@Composable
fun MoviesScreen(
    navController: NavController,
    viewModel: MoviesViewModel = hiltViewModel()
) {
    val moviesList = viewModel.movies.collectAsState()
    val searchText = viewModel.searchText.collectAsState()
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize()
    ) { contentPadding ->
        Column {
            BasicTextField(Modifier.testTag(SEARCH_TAG))
            CategorizedLazyColumn(
                movies = moviesList.value,
                searchText = searchText.value
            ) { title ->
                navController.navigate(
                    Screen.MovieDetailScreen.route +
                            "?movieTitle=${title}"
                )
            }
        }
    }
}


@Composable
private fun CategoryHeader(
    text: String,
    modifier: Modifier = Modifier
) {
    androidx.compose.material3.Text(
        text = text,
        style = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        ),
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(16.dp)
    )
}

@Composable
private fun CategoryItem(
    text: String,
    modifier: Modifier = Modifier
) {
    androidx.compose.material3.Text(
        text = text,
        fontSize = 14.sp,
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun CategorizedLazyColumn(
    movies: List<Movie>,
    modifier: Modifier = Modifier,
    searchText: String = "",
    onItemClick: (String) -> Unit = {},
) {
    LazyColumn(
        modifier = modifier.testTag(Constants.MOVIES_LIST_TAG)
    ) {
        if (searchText.isNotBlank()) {
            movies.categorize().forEach { category ->
                stickyHeader {
                    CategoryHeader(category.name)
                }
                items(category.movies) { movie ->
                    CategoryItem(movie.title,
                        modifier = Modifier
                            .clickable(true) {
                                movie.title.let { onItemClick.invoke(it) }
                            })
                }
            }
            return@LazyColumn
        }
        items(movies) { movie ->
            CategoryItem(movie.title,
                modifier = Modifier
                    .clickable(true) {
                        movie.title.let { onItemClick.invoke(it) }
                    })
        }
    }
}