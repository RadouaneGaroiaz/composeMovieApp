package com.aldikitta.jetmvvmmovie.ui.screens.moviedetail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aldikitta.jetmvvmmovie.data.model.FavoriteBody
import com.aldikitta.jetmvvmmovie.data.model.moviedetail.MovieDetail
import com.aldikitta.jetmvvmmovie.data.repository.MovieRepository
import com.aldikitta.jetmvvmmovie.utils.network.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(private val repository: MovieRepository) :
    ViewModel() {
    val movieDetail: MutableState<DataState<MovieDetail>?> = mutableStateOf(null)
    val _isFavorite: MutableState<DataState<Boolean>?> = mutableStateOf(null)

    //detail movie
    fun movieDetailApi(movieId: Int) {
        viewModelScope.launch {
            repository.movieDetail(movieId).onEach {
                movieDetail.value = it
            }.launchIn(viewModelScope)
        }
    }

    // Get movie favorite status
    fun fetchMovieState(movieId: Int) {
        viewModelScope.launch {
            val response = repository.getMovieState(movieId) // Make sure to implement this in your repository
            if(response is DataState.Success) {
                _isFavorite.value = DataState.Success(response.data.isFavorite)
            }
        }
    }

    // Update movie favorite status
    fun updateFavoriteState(movieId: Int, isFavorite: Boolean) {
        viewModelScope.launch {
            val favoriteBody = FavoriteBody(mediaId = movieId, isFavorite = isFavorite)
            val response = repository.postFavorite(movieId, favoriteBody) // Make sure to implement this in your repository
            if(response is DataState.Success) {
                _isFavorite.value = DataState.Success(isFavorite)
            }
        }
    }



}