package com.fnd.miflix.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "content")
data class ContentEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val overview: String,
    @SerializedName("poster_path") val posterPath: String,
    @ColumnInfo(name = "release_date") val release_date: String,
    val vote_average: Double
)