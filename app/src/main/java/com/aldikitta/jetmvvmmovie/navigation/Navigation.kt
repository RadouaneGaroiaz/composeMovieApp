package com.aldikitta.jetmvvmmovie.navigation

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.aldikitta.jetmvvmmovie.R
import com.aldikitta.jetmvvmmovie.ui.screens.bottombar.home.NowPlaying
import com.aldikitta.jetmvvmmovie.ui.screens.bottombar.libary.Upcoming
import com.aldikitta.jetmvvmmovie.ui.screens.moviedetail.MovieDetail



@Composable
fun Navigation(
    navController: NavHostController,
    modifier: Modifier,
) {
    val genreName = remember {
        mutableStateOf("")
    }

    NavHost(navController, startDestination = "home", modifier) {
        composable(NavigationScreen.HOME) {
            NowPlaying(
                navController = navController,
            )
        }
        composable(NavigationScreen.UP_COMING) {
            Upcoming(
                navController = navController
            )
        }
        composable(
            NavigationScreen.MovieDetail.MOVIE_DETAIL.plus(NavigationScreen.MovieDetail.MOVIE_DETAIL_PATH),
            arguments = listOf(navArgument(NavigationScreen.MovieDetail.MOVIE_ITEM) {
                type = NavType.IntType
            })
        ) {
            label = stringResource(R.string.movie_detail)
            val movieId =
                it.arguments?.getInt(NavigationScreen.MovieDetail.MOVIE_ITEM)
            if (movieId != null) {
                MovieDetail(
                    navController = navController,
                    movieId
                )
            }
        }

    }
}

@Composable
fun navigationTitle(navController: NavController): String {
    return when (currentRoute(navController)) {
        NavigationScreen.MovieDetail.MOVIE_DETAIL -> stringResource(id = R.string.movie_detail)

        else -> {
            ""
        }
    }
}

@Composable
fun currentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route?.substringBeforeLast("/")
}
