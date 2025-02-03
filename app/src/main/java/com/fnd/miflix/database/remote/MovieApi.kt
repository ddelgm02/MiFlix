package com.fnd.miflix.database.remote


import com.fnd.miflix.models.ContentEntity
import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import retrofit2.http.Query


interface MovieApi {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String = "",
        @Query("language") language: String = "es-ES",
        @Query("page") page: Int = 1
    ): MovieResponse

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("api_key") apiKey: String = "",
        @Query("query") query: String = "",  // Nombre de la película a buscar
        @Query("language") language: String = "es-ES",
        @Query("page") page: Int = 1
    ): MovieResponse
}

data class MovieResponse (
    val page: Int,
    val results: List<ContentEntity>,
    @SerializedName("total_pages") val totalPages: Int
)
