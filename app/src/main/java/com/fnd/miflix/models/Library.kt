package com.fnd.miflix.models

data class Library(
    val id: String,
    val title: String,
    val imageUrl : String,
    val cast: List<String>,
    val synopsis: String?,
    val rating: Double?,
    val genres: List<String>,
    val type: String
)