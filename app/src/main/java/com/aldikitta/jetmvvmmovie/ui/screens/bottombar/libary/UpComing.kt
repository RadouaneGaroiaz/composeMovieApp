package com.aldikitta.jetmvvmmovie.ui.screens.bottombar.libary

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController


@Composable
fun Upcoming(
    navController: NavController,
) {
    val upComingViewModel = hiltViewModel<UpComingViewModel>()
//    HomeScreen(
//        navController = navController,
//        //movies = upComingViewModel.upcomingMovies
//    )
}