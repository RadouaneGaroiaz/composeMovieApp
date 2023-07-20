package com.aldikitta.jetmvvmmovie.ui.screens.moviedetail

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.filled.StarHalf
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.aldikitta.jetmvvmmovie.R
import com.aldikitta.jetmvvmmovie.data.datasource.remote.ApiURL
import com.aldikitta.jetmvvmmovie.data.model.moviedetail.MovieDetail
import com.aldikitta.jetmvvmmovie.ui.components.CircularIndeterminateProgressBar
import com.aldikitta.jetmvvmmovie.ui.components.appbar.AppBarWithArrow
import com.aldikitta.jetmvvmmovie.utils.network.DataState
import com.aldikitta.jetmvvmmovie.utils.pagingLoadingState

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetail(navController: NavController, movieId: Int) {
    val movieDetailViewModel = hiltViewModel<MovieDetailViewModel>()
    val progressBar = remember {
        mutableStateOf(false)
    }
    val movieDetail = movieDetailViewModel.movieDetail

    LaunchedEffect(key1 = true) {
        movieDetailViewModel.movieDetailApi(movieId)
    }

    Scaffold(
        topBar = {
            movieDetail.value?.let { it ->
                if (it is DataState.Success<MovieDetail>) {
                    AppBarWithArrow(
                        title = it.data.title,
                        pressOnBack = { navController.popBackStack() }
                    )
                }
            }
        }
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            CircularIndeterminateProgressBar(isDisplayed = progressBar.value, verticalBias = 0.4f)
            movieDetail.value?.let { it ->
                if (it is DataState.Success<MovieDetail>) {
                    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                        Image(
                            painter = rememberAsyncImagePainter(ApiURL.IMAGE_URL.plus(it.data.backdrop_path)),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .height(250.dp)
                                .padding(start = 5.dp, end = 5.dp)
                                .clip(MaterialTheme.shapes.large)
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(start = 15.dp, end = 15.dp)
                        ) {
                            Text(
                                text = it.data.title,
                                modifier = Modifier.padding(top = 10.dp),
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 2,
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(bottom = 10.dp, top = 10.dp)
                            ) {
                                Column(
                                    Modifier.weight(1f),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = stringResource(R.string.language),
                                        fontWeight = FontWeight.Bold

                                    )
                                    Text(
                                        text = it.data.original_language,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Column(
                                    Modifier.weight(1f),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    RatingBar(current = it.data.vote_average.toFloat() / 2) // Assuming your rating is out of 10
                                    Text(
                                        text = it.data.vote_average.toString(),
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Column(
                                    Modifier.weight(1f),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = stringResource(R.string.release_date),
                                        fontWeight = FontWeight.Bold

                                    )
                                    Text(
                                        text = it.data.release_date,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                            Text(
                                text = stringResource(R.string.description),
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.titleLarge
                            )
                            Text(
                                text = it.data.overview,
                                modifier = Modifier.padding(bottom = 10.dp)
                            )
                        }
                    }
                }
            }
            movieDetail.pagingLoadingState {
                progressBar.value = it
            }
        }
    }
}



@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    max: Int = 10,
    starSize: Dp = 30.dp,
    current: Float,
    activeColor: Color = Color.Green,
    inActiveColor: Color = Color.Gray,
) {
    Row(modifier = modifier) {
        val fullStars = current.toInt()
        val halfStar = if(current - fullStars >= 0.5) 1 else 0
        val emptyStars = max - fullStars - halfStar

        repeat(fullStars) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = "Star",
                tint = activeColor,
                modifier = Modifier.size(starSize)
            )
        }

        if (halfStar != 0) {
            Icon(
                imageVector = Icons.Filled.StarHalf,
                contentDescription = "Half Star",
                tint = activeColor,
                modifier = Modifier.size(starSize)
            )
        }

        repeat(emptyStars) {
            Icon(
                imageVector = Icons.Filled.StarBorder,
                contentDescription = "Empty Star",
                tint = inActiveColor,
                modifier = Modifier.size(starSize)
            )
        }
    }
}







