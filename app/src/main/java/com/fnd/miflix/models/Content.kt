package com.fnd.miflix.models

sealed class Content {

    abstract val id: Int
    abstract val title: String
    abstract val overview: String
    abstract val posterPath: String
    abstract val releaseDate: String

    data class Movie(
        override val id: Int,
        override val title: String,
        override val overview: String,
        override val posterPath: String,
        override val releaseDate: String,
        val runtime: Int // Propiedad específica de películas
    ) : Content()

    data class Serie(
        override val id: Int,
        override val title: String,
        override val overview: String,
        override val posterPath: String,
        override val releaseDate: String,
        val numberOfSeasons: Int // Propiedad específica de series
    ) : Content()

}