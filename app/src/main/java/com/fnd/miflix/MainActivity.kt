package com.fnd.miflix

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
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
                setBarColor(color = MaterialTheme.colorScheme.background)
                NavigationHost(navController = navController, userDao)
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
    private fun NavigationHost(navController: NavHostController, userDao: UserDao) {
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
                    HomeScreen(usuario = it, navController = navController)
                }
            }
            composable("signup") {
                SignUpScreen(navController = navController)
            }
        }
    }
}
