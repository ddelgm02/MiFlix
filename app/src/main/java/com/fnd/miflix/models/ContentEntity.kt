package com.fnd.miflix.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "content")
data class ContentEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val overview: String,
    @ColumnInfo(name = "poster_path") val posterPath: String?,
    @ColumnInfo(name = "release_date") val releaseDate: String,
    val contentType: String, // "movie" o "tv"
    val runtime: Int? = null, // Solo para películas
    @ColumnInfo(name = "number_of_seasons") val numberOfSeasons: Int? = null // Solo para series
)