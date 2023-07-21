package com.aldikitta.jetmvvmmovie.ui.components


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable

import com.aldikitta.jetmvvmmovie.navigation.NavigationScreen

sealed class NavigationItem(
    val route: String,
    val icon: @Composable () -> Unit,
    val title: String,
) {
    object Home : NavigationItem(
        NavigationScreen.HOME, {
            Icon(
                Icons.Filled.Home,
                contentDescription = "Home",
            )
        }, "Home"
    )



    object UpComing : NavigationItem(
        NavigationScreen.Library,
        {
            Icon(
                Icons.Filled.Upcoming,
                contentDescription = "Library"
            )
        },
        "Library",
    )
}