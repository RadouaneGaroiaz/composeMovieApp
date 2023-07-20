package com.aldikitta.jetmvvmmovie.data.model.artist


import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("status_code")
    val status_code: Int,

    @SerializedName("status_message")
    val status_message: String
)
