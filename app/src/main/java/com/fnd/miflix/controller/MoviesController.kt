package com.fnd.miflix.controller

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fnd.miflix.database.remote.RetrofitClient
import com.fnd.miflix.models.Movie
import kotlinx.coroutines.launch

class MoviesController : ViewModel() {

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> = _movies

    fun fetchPopularMovies() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.movieApi.getPopularMovies()
                Log.d("MoviesController", "Response: ${response.results}") // Agregar un log aquí
                _movies.postValue(response.results)
            } catch (e: Exception) {
                // Maneja el error (por ejemplo, mostrando un mensaje)
                Log.e("MoviesController", "Error fetching movies", e)
            }
        }
    }
}




