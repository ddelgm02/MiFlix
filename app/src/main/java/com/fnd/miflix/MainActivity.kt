package com.fnd.miflix

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import com.fnd.miflix.ui.theme.MiFlixTheme
import com.fnd.miflix.views.LoginScreen
import com.fnd.miflix.views.SignUpScreen
import com.fnd.miflix.views.HomeScreen
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import androidx.navigation.NavHostController
import androidx.navigation.compose.*

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MiFlixTheme {
                val navController = rememberNavController()

                setBarColor(color = MaterialTheme.colorScheme.background )
                NavigationHost(navController = navController)
            }
        }
    }

    @Composable
    private fun setBarColor(color: Color){
        val systemUiController = rememberSystemUiController()
        SideEffect {
            systemUiController.setStatusBarColor(
                color= color
            )
        }

    }

    @Composable
    private fun NavigationHost(navController: NavHostController) {
        // Configuración de las pantallas de la aplicación
        NavHost(navController = navController, startDestination = "login") {
            composable("login") {
                LoginScreen(
                    onLoginSuccess = {
                        navController.navigate("home") // Navegar a la pantalla de inicio
                    },
                    onSignUpClick = {
                        navController.navigate("signup") // Navegar a la pantalla de registro
                    }
                )
            }
            composable("home") {
                HomeScreen() // La pantalla de inicio
            }
            composable("signup") {
                SignUpScreen(navController = navController) // Pantalla de registro
            }
        }
    }
}