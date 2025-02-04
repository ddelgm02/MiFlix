package com.example.app

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

// Definimos los datos de los usuarios en memoria
val usuarios = mutableListOf<Usuario>(
    Usuario(1, "Carlos", "carlos@gmail.com", "password", esAdmin = true),
    Usuario(2, "Maria", "maria@gmail.com", "password", esAdmin = false)
)

data class Usuario(
    val id: Int,
    val nombre: String,
    val email: String,
    val password: String,
    var esAdmin: Boolean
)

@Composable
fun AdminScreen(navController: NavHostController) {
    // Usamos directamente la lista de usuarios (sin obtenerla desde una función extra)
    val usuariosList = usuarios

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Button(onClick = { navController.navigate("contentAdmin") }) {
            Text("Administrar Contenido")
        }
        Text("Gestionar Usuarios", fontSize = 24.sp)

        Spacer(modifier = Modifier.height(16.dp))

        // Iteramos sobre los usuarios y mostramos los datos
        usuariosList.forEach { usuario ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(usuario.nombre, modifier = Modifier.weight(1f))
                Text(if (usuario.esAdmin) "Admin" else "Usuario")
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = {
                    // Cambiar el rol de admin
                    usuario.esAdmin = !usuario.esAdmin
                }) {
                    Text(if (usuario.esAdmin) "Revertir a Usuario" else "Hacer Admin")
                }
            }
            Divider(modifier = Modifier.padding(vertical = 8.dp))
        }
    }
}

@Composable
fun Navigation(navController: NavHostController) {
    // Definimos las pantallas de navegación
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(navController = navController)
        }
        composable("admin") {
            AdminScreen(navController = navController)
        }
    }
}

@Composable
fun HomeScreen(navController: NavHostController) {
    val usuario = usuarios[0] // Aquí obtienes al usuario actual

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Bienvenido, ${usuario.nombre}!", fontSize = 24.sp)

        Spacer(modifier = Modifier.height(16.dp))

        // Si es admin, muestra el botón para ir a la pantalla de administración
        if (usuario.esAdmin) {
            Button(onClick = { navController.navigate("admin") }) {
                Text("Gestionar Usuarios")
            }
        } else {
            Text("Eres un usuario normal.")
        }
    }
}

@Composable
@Preview(showBackground = true)
fun DefaultPreview() {
    val navController = rememberNavController()
    Navigation(navController)
}
