import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.Divider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

// Ejemplo de la base de datos en memoria
val usuarios = mutableListOf<Usuario>(
    Usuario(1, "Carlos", "carlos@example.com", "password", esAdmin = true),
    Usuario(2, "Maria", "maria@example.com", "password", esAdmin = false)
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Usamos directamente MaterialTheme sin MyApplicationTheme
            MaterialTheme(
                colorScheme = lightColorScheme(
                    primary = Color(0xFF6200EE),
                    secondary = Color(0xFF03DAC6)
                )
            ) {
                // Configuramos la navegación
                val navController = rememberNavController()

                // Llamamos a la función que maneja la navegación entre pantallas
                Navigation(navController)
            }
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
fun AdminScreen(navController: NavHostController) {
    val usuario = usuarios[0] // Asumimos que el admin es el primer usuario

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Gestionar Usuarios", fontSize = 24.sp)

        Spacer(modifier = Modifier.height(16.dp))

        // Muestra los usuarios actuales
        usuarios.forEach { usuario ->
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

data class Usuario(
    val id: Int,
    val nombre: String,
    val email: String,
    val password: String,
    var esAdmin: Boolean
)
