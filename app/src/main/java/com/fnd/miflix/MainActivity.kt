package com.fnd.miflix

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import com.fnd.miflix.ui.theme.MiFlixTheme
import com.fnd.miflix.views.LoginScreen
import com.fnd.miflix.views.SignUpScreen
import com.fnd.miflix.views.HomeScreen
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.fnd.miflix.database.AppDatabase
import com.fnd.miflix.models.DAO.UserDao
import com.fnd.miflix.models.User
import kotlinx.coroutines.launch
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fnd.miflix.controller.MoviesController
import com.fnd.miflix.models.Movie
import com.fnd.miflix.views.MovieDetailScreen

class MainActivity : ComponentActivity() {
    private val userDao: UserDao by lazy {
        AppDatabase.getDatabase(application).usuariosDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MiFlixTheme {
                val navController = rememberNavController()
                val moviesController: MoviesController = viewModel() // Obtenemos el ViewModel de las películas
                val moviesList by moviesController.movies.observeAsState(initial = emptyList()) // Lista de películas

                LaunchedEffect(moviesList) {
                    moviesController.fetchPopularMovies()
                }

                setBarColor(color = MaterialTheme.colorScheme.background)
                NavigationHost(navController = navController, userDao, moviesList)
            }
        }
    }

    @Composable
    private fun setBarColor(color: Color) {
        val systemUiController = rememberSystemUiController()
        SideEffect {
            systemUiController.setStatusBarColor(color = color)
        }
    }

    @Composable
    fun NavigationHost(navController: NavHostController, userDao: UserDao, moviesList: List<Movie>) {
        NavHost(navController = navController, startDestination = "login") {
            composable("login") {
                LoginScreen(
                    onLoginSuccess = { usuario ->
                        navController.navigate("home/${usuario.email}") // Navegar a la pantalla de inicio
                    },
                    onSignUpClick = {
                        navController.navigate("signup") // Navegar a la pantalla de registro
                    }
                )
            }
            composable("home/{email}") { backStackEntry ->
                val email = backStackEntry.arguments?.getString("email") ?: ""
                var usuario by remember { mutableStateOf<User?>(null) }

                LaunchedEffect(email) { // Ejecutar la función suspendida en una corrutina
                    usuario = userDao.getUsersByEmail(email)
                }

                usuario?.let {
                    HomeScreen(usuario = it, navController = navController, moviesList = moviesList)
                }
            }
            composable("signup") {
                SignUpScreen(navController = navController)
            }
            composable("movie_details/{movieId}") { backStackEntry ->
                val movieId = backStackEntry.arguments?.getString("movieId")?.toInt() ?: 0
                val movie = moviesList.firstOrNull { it.id == movieId } // Obtener la película desde la lista
                if (movie != null) {
                    MovieDetailScreen(movie = movie, navController = navController)
                }
            }
        }
    }

}

