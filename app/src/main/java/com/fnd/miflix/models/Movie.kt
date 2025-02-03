package com.fnd.miflix.models

import com.google.gson.annotations.SerializedName

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    @SerializedName("poster_path") val posterPath: String,
    val release_date: String,
    val vote_average: Double
)
