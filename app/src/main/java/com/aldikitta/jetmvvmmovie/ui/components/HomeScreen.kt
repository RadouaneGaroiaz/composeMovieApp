package com.aldikitta.jetmvvmmovie.ui.components

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.rememberAsyncImagePainter
import com.aldikitta.jetmvvmmovie.data.datasource.remote.ApiURL
import com.aldikitta.jetmvvmmovie.data.model.MovieItem
import com.aldikitta.jetmvvmmovie.navigation.NavigationScreen
import com.aldikitta.jetmvvmmovie.navigation.currentRoute
import com.aldikitta.jetmvvmmovie.utils.PagingLoadingState
import com.aldikitta.jetmvvmmovie.utils.items
import kotlinx.coroutines.flow.Flow

@Composable
fun HomeScreen(
    navController: NavController,
    popularMovies: Flow<PagingData<MovieItem>>,
    topRatedMovies: Flow<PagingData<MovieItem>>,
    upcomingMovies: Flow<PagingData<MovieItem>>
) {
    val activity = (LocalContext.current as? Activity)
    val progressBar = remember {
        mutableStateOf(false)
    }
    val openDialog = remember {
        mutableStateOf(false)
    }

    val popularMoviesItems: LazyPagingItems<MovieItem> = popularMovies.collectAsLazyPagingItems()
    val topRatedMoviesItems: LazyPagingItems<MovieItem> = topRatedMovies.collectAsLazyPagingItems()
    val upcomingMoviesItems: LazyPagingItems<MovieItem> = upcomingMovies.collectAsLazyPagingItems()

    BackHandler(enabled = (currentRoute(navController) === NavigationScreen.HOME)) {
        //login screen
        openDialog.value = true
    }

    Column {
        CircularIndeterminateProgressBar(isDisplayed = progressBar.value, verticalBias = 0.4f)

        LazyColumn(modifier = Modifier.padding(start = 5.dp, top = 5.dp, end = 5.dp)) {

            item {
                Text("Popular Movies", style = MaterialTheme.typography.bodyLarge,fontWeight = FontWeight.Bold)
                LazyRow {
                    items(popularMoviesItems) { item ->
                        item?.let {
                            MovieItemView(item, navController)
                        }
                    }
                }
            }

            item {
                Text("Top Rated Movies", style = MaterialTheme.typography.bodyLarge,fontWeight = FontWeight.Bold)
                LazyRow {
                    items(topRatedMoviesItems) { item ->
                        item?.let {
                            MovieItemView(item, navController)
                        }
                    }
                }
            }

            item {
                Text("Upcoming Movies", style = MaterialTheme.typography.bodyLarge,fontWeight = FontWeight.Bold)
                LazyRow {
                    items(upcomingMoviesItems) { item ->
                        item?.let {
                            MovieItemView(item, navController)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MovieItemView(item: MovieItem, navController: NavController) {
    Column(modifier = Modifier.padding(7.5.dp)) {
        Image(painter = rememberAsyncImagePainter(ApiURL.IMAGE_URL.plus(item.posterPath)),
            contentDescription = "movieItemView",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(250.dp)
                .clip(MaterialTheme.shapes.large)
                .clickable {
                    navController.navigate(NavigationScreen.MovieDetail.MOVIE_DETAIL.plus("/${item.id}"))
                }
        )
        Text(text = item.title)
    }
}
