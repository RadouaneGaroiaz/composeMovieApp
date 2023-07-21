package com.aldikitta.jetmvvmmovie.data.model

import com.google.gson.annotations.SerializedName

data class MovieStateResponse(
    @SerializedName("favorite") val isFavorite: Boolean,
    // other fields
)

data class FavoriteBody(
    @SerializedName("media_type") val mediaType: String = "movie",
    @SerializedName("media_id") val mediaId: Int,
    @SerializedName("favorite") val isFavorite: Boolean
)