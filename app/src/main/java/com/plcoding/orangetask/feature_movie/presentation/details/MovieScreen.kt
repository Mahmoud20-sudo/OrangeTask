package com.plcoding.orangetask.feature_movie.presentation.details

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.plcoding.orangetask.feature_movie.data.model.flickr.Photo
import com.plcoding.orangetask.feature_movie.util.Constants.LIST_COLUMN_COUNT
import com.plcoding.orangetask.feature_movie.util.itemsPaging

@Composable
fun MovieScreen(
    navController: NavController,
    viewModel: MovieViewModel = hiltViewModel()
) {
    val movie = viewModel.movie.collectAsState()
    val photos: LazyPagingItems<Photo> = viewModel.photosData.collectAsLazyPagingItems()

    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    val showShimmer = remember { mutableStateOf(true) }

    Scaffold(
        backgroundColor = Color.White,
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(title = {
                Text(
                    text = "${movie.value?.title}",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth(),
                    color = Color.White
                )
            }, backgroundColor = MaterialTheme.colorScheme.primary)
        }
    ) { contentPadding ->
        Column(modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 20.dp)) {
            movie.value?.year?.let {
                Row {
                    Text(
                        text = "Year:",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    )

                    Text(
                        text = "$it",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 16.sp
                        ),
                        modifier = Modifier.padding(5.dp)
                    )
                }
            }


            movie.value?.genres?.let { genres ->
                Text(
                    text = "Genres:",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                )

                Text(
                    text = genres.joinToString { it },
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 16.sp
                    ),
                    modifier = Modifier.padding(5.dp)
                )
            }

            movie.value?.cast?.let { cast ->
                Text(
                    text = "Cast:",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                )

                Text(
                    text = cast.joinToString { it },
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 16.sp
                    ),
                    modifier = Modifier.padding(5.dp)
                )
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(LIST_COLUMN_COUNT),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(top = 16.dp),
                modifier = Modifier.weight(1F)
            ) {
                itemsPaging(photos) { photo: Photo? ->
                    AsyncImage(
                        model = photo?.getImageUrl(),
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .background(
                                shimmerBrush(
                                    targetValue = 1300f,
                                    showShimmer = showShimmer.value
                                )
                            )
                            .clip(RoundedCornerShape(5.dp))
                            .height(200.dp)
                            .fillMaxWidth(),
                        onSuccess = { showShimmer.value = false },
                    )
                }
            }
        }
    }
}

@Composable
private fun shimmerBrush(showShimmer: Boolean = true, targetValue: Float = 1000f): Brush {
    return if (showShimmer) {
        val shimmerColors = listOf(
            Color.LightGray.copy(alpha = 0.6f),
            Color.LightGray.copy(alpha = 0.2f),
            Color.LightGray.copy(alpha = 0.6f),
        )

        val transition = rememberInfiniteTransition(label = "")
        val translateAnimation = transition.animateFloat(
            initialValue = 0f,
            targetValue = targetValue,
            animationSpec = infiniteRepeatable(
                animation = tween(800), repeatMode = RepeatMode.Reverse
            ), label = ""
        )
        Brush.linearGradient(
            colors = shimmerColors,
            start = Offset.Zero,
            end = Offset(x = translateAnimation.value, y = translateAnimation.value)
        )
    } else {
        Brush.linearGradient(
            colors = listOf(Color.Transparent, Color.Transparent),
            start = Offset.Zero,
            end = Offset.Zero
        )
    }
}