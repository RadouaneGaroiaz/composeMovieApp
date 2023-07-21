package com.aldikitta.jetmvvmmovie.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.aldikitta.jetmvvmmovie.data.datasource.remote.paging.*
import com.aldikitta.jetmvvmmovie.data.datasource.remote.ApiService
import com.aldikitta.jetmvvmmovie.data.model.BaseModel
import com.aldikitta.jetmvvmmovie.data.model.FavoriteBody
import com.aldikitta.jetmvvmmovie.data.model.Genres
import com.aldikitta.jetmvvmmovie.data.model.MovieStateResponse
import com.aldikitta.jetmvvmmovie.data.model.moviedetail.MovieDetail
import com.aldikitta.jetmvvmmovie.utils.network.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun movieList(page: Int): Flow<DataState<BaseModel>> = flow {
        emit(DataState.Loading)
        try {
            val searchResult = apiService.nowPlayingMovieList(page)
            emit(DataState.Success(searchResult))

        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    suspend fun movieDetail(movieId: Int): Flow<DataState<MovieDetail>> = flow {
        emit(DataState.Loading)
        try {
            val searchResult = apiService.movieDetail(movieId)
            emit(DataState.Success(searchResult))

        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    suspend fun recommendedMovie(movieId: Int, page: Int): Flow<DataState<BaseModel>> = flow {
        emit(DataState.Loading)
        try {
            val searchResult = apiService.recommendedMovie(movieId, page)
            emit(DataState.Success(searchResult))

        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }


    suspend fun search(searchKey: String): Flow<DataState<BaseModel>> = flow {
        emit(DataState.Loading)
        try {
            val searchResult = apiService.search(searchKey)
            emit(DataState.Success(searchResult))

        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    suspend fun genreList(): Flow<DataState<Genres>> = flow {
        emit(DataState.Loading)
        try {
            val genreResult = apiService.genreList()
            emit(DataState.Success(genreResult))

        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    suspend fun moviesByGenreId(page: Int, genreId: String): Flow<DataState<BaseModel>> = flow {
        emit(DataState.Loading)
        try {
            val genreResult = apiService.moviesByGenre(page, genreId)
            emit(DataState.Success(genreResult))

        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    suspend fun getMovieState(movieId: Int): DataState<MovieStateResponse> {
        return try {
            val response = apiService.getMovieState(movieId)
            DataState.Success(response)
        } catch (e: Exception) {
            DataState.Error(e)
        }
    }

    suspend fun postFavorite(movieId: Int, favoriteBody: FavoriteBody): DataState<Unit> {
        return try {
            val response = apiService.postFavorite(movieId, favoriteBody)
            if (response.isSuccessful) {
                DataState.Success(Unit)
            } else {
                DataState.Error(Exception("Failed to update favorite status"))
            }
        } catch (e: Exception) {
            DataState.Error(e)
        }
    }





    fun nowPlayingPagingDataSource() = Pager(
        pagingSourceFactory = { NowPlayingPagingDataSource(apiService) },
        config = PagingConfig(pageSize = 1)
    ).flow

    fun popularPagingDataSource() = Pager(
        pagingSourceFactory = { PopularPagingDataSource(apiService) },
        config = PagingConfig(pageSize = 1)
    ).flow

    fun topRatedPagingDataSource() = Pager(
        pagingSourceFactory = { TopRatedPagingDataSource(apiService) },
        config = PagingConfig(pageSize = 1)
    ).flow

    fun upcomingPagingDataSource() = Pager(
        pagingSourceFactory = { UpcomingPagingDataSource(apiService) },
        config = PagingConfig(pageSize = 1)
    ).flow



}