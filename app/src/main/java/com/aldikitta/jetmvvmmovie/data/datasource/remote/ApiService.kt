package com.aldikitta.jetmvvmmovie.data.datasource.remote

import com.aldikitta.jetmvvmmovie.data.model.BaseModel
import com.aldikitta.jetmvvmmovie.data.model.FavoriteBody
import com.aldikitta.jetmvvmmovie.data.model.Genres
import com.aldikitta.jetmvvmmovie.data.model.MovieStateResponse
import com.aldikitta.jetmvvmmovie.data.model.moviedetail.MovieDetail
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET(ApiURL.MOVIE_LIST)
    suspend fun nowPlayingMovieList(@Query("page") page: Int): BaseModel

    @GET(ApiURL.POPULAR_MOVIE_LIST)
    suspend fun popularMovieList(@Query("page") page: Int): BaseModel

    @GET(ApiURL.TOP_RATED_MOVIE_LIST)
    suspend fun topRatedMovieList(@Query("page") page: Int): BaseModel

    @GET(ApiURL.UP_COMING_MOVIE_LIST)
    suspend fun upcomingMovieList(@Query("page") page: Int): BaseModel

    @GET(ApiURL.MOVIE_DETAIL)
    suspend fun movieDetail(@Path("movieId") movieId: Int): MovieDetail

    @GET(ApiURL.RECOMMENDED_MOVIE)
    suspend fun recommendedMovie(@Path("movieId") movieId: Int, @Query("page") one: Int): BaseModel

    @GET(ApiURL.SEARCH_MOVIE)
    suspend fun search(@Query("query") searchKey: String): BaseModel

    @GET(ApiURL.GENRE_LIST)
    suspend fun genreList(): Genres

    @GET(ApiURL.GENRE_MOVIES_BY_ID)
    suspend fun moviesByGenre(
        @Query("page") page: Int,
        @Query("with_genres") genreId: String
    ): BaseModel


    @GET(ApiURL.FAVORITE_MOVIE)
    suspend fun getMovieState(@Path("movie_id") movieId: Int): MovieStateResponse

    @POST(ApiURL.FAVORITE_MOVIE_POST)
    suspend fun postFavorite(@Path("movie_id") movieId: Int, @Body body: FavoriteBody): Response<Unit>

}