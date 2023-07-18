package com.aldikitta.jetmvvmmovie.navigation

object NavigationScreen {
    const val HOME = "home"
    const val POPULAR = "popular"
    const val TOP_RATED = "toprated"
    const val UP_COMING = "upcoming"

    object MovieDetail {
        const val MOVIE_DETAIL = "movieDetail"
        const val MOVIE_ITEM = "movieItem"
        const val MOVIE_DETAIL_PATH = "/{movieItem}"
    }
    object ArtistDetail {
        const val ARTIST_DETAIL = "artistDetail"
        const val ARTIST_ID = "artistId"
        const val ARTIST_DETAIL_PATH = "/{artistId}"
    }
}