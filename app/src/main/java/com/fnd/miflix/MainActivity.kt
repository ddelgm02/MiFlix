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
import com.fnd.miflix.models.ContentEntity
import com.fnd.miflix.views.FollowingScreen
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
                NavigationHost(navController = navController, userDao = userDao, moviesList = moviesList)
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
    fun NavigationHost(navController: NavHostController, userDao: UserDao, moviesList: List<ContentEntity>) {
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
                val moviesController: MoviesController = viewModel()

                // Carga el usuario y navega a la pantalla de inicio
                LaunchedEffect(email) {
                    usuario = userDao.getUsersByEmail(email)
                }

                if (usuario != null) {
                    HomeScreen(usuario = usuario!!, navController = navController, moviesList = moviesList, moviesController = moviesController)
                }
            }
            composable("signup") {
                SignUpScreen(navController = navController)
            }
            composable("movie_details/{movieId}?email={email}") { backStackEntry ->
                val movieId = backStackEntry.arguments?.getString("movieId")?.toInt() ?: 0
                val email = backStackEntry.arguments?.getString("email") ?: ""

                // Variables de estado
                var movie by remember { mutableStateOf<ContentEntity?>(null) }
                var usuario by remember { mutableStateOf<User?>(null) }

                val moviesController: MoviesController = viewModel()
                // Cargar el usuario
                LaunchedEffect(email) {
                    usuario = userDao.getUsersByEmail(email)
                }

                // Cargar la película correspondiente a movieId
                LaunchedEffect(movieId) {
                    movie = moviesList.firstOrNull { it.id == movieId }
                }

                // Esperar a que tanto el usuario como la película estén disponibles
                if (usuario != null && movie != null) {
                    // Ahora, navega a la pantalla de detalles cuando los datos estén listos
                    MovieDetailScreen(movie = movie!!, navController = navController, user = usuario, moviesController = moviesController)
                }
            }
            composable("following/{email}") { backStackEntry ->
                var usuario by remember { mutableStateOf<User?>(null) }
                val email = backStackEntry.arguments?.getString("email") ?: ""
                val moviesController: MoviesController = viewModel()

                LaunchedEffect(email) {
                    usuario = userDao.getUsersByEmail(email)
                }

                // Si el usuario está cargado, mostrar la pantalla de seguimiento
                if (usuario != null) {
                    FollowingScreen(usuario = usuario!!, navController = navController, moviesController = moviesController)
                }
            }
        }
    }
}

