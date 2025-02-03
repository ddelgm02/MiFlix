package com.fnd.miflix.controller

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fnd.miflix.database.AppDatabase
import com.fnd.miflix.database.remote.RetrofitClient
import com.fnd.miflix.models.ContenidoSeguido
import com.fnd.miflix.models.ContentEntity
import kotlinx.coroutines.launch
import com.fnd.miflix.models.DAO.*
import com.fnd.miflix.models.DAO.ContentDao
import com.fnd.miflix.models.User


class MoviesController (application: Application) : AndroidViewModel(application) {
    private val contenidoSeguidoDao = AppDatabase.getDatabase(application).contenidoSeguidoDao()
    private val userDao = AppDatabase.getDatabase(application).usuariosDao()
    private val contentDao = AppDatabase.getDatabase(application).contentDao()

    private val _movies = MutableLiveData<List<ContentEntity>>()
    val movies: LiveData<List<ContentEntity>> = _movies

    fun fetchPopularMovies() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.movieApi.getPopularMovies()
                _movies.postValue(response.results)
            } catch (e: Exception) {
                Log.e("MoviesController", "Error fetching movies", e)
            }
        }
    }

    suspend fun isContenidoSeguido(userId: Int, contentId: Int): ContenidoSeguido? {
        return contenidoSeguidoDao.isContenidoSeguido(userId, contentId)
    }

    suspend fun addContenidoSeguido(user: User, content: ContentEntity) {
        // Lógica para agregar la película a la base de datos como seguida por el usuario
        contentDao.insertContent(content)
        val user = userDao.getUserById(user.id)
        val movie = contentDao.getContentById(content.id)
        user?.let { movie?.let { movie -> contenidoSeguidoDao.insertContenidoSeguido(ContenidoSeguido(user.id,movie.id)) } }
    }

    // Función para eliminar contenido seguido
    suspend fun removeContenidoSeguido(userId: Int, movieId: Int) {
        // Lógica para eliminar la película de la base de datos como seguida por el usuario
        contenidoSeguidoDao.removeContenidoSeguido(userId, movieId)
    }

    suspend fun getAllContenidosSeguidos(userId: Int): List<ContentEntity> {
        return contenidoSeguidoDao.getAllContenidosSeguidos(userId)
            .mapNotNull { contenidoSeguido ->
                contentDao.getContentById(contenidoSeguido.contentId) // Buscar el contenido real
            }
    }

    fun buscarPeliculas(query: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.movieApi.searchMovies(query = query)
                _movies.postValue(response.results)
                // Actualizamos el LiveData con los resultados
            } catch (e: Exception) {
                Log.e("MoviesController", "Error bucando peliculas", e)
            }
        }
    }

}




