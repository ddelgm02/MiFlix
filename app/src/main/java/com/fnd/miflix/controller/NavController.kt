import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

// Ejemplo de la base de datos en memoria
val usuarios = mutableListOf<Usuario>(
    Usuario(1, "Carlos", "carlos@example.com", "password", esAdmin = true),
    Usuario(2, "Maria", "maria@example.com", "password", esAdmin = false)
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Integración del NavController en tu MainActivity
            val navController = rememberNavController()

            // Pantalla de navegación
            Navigation(navController)
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
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
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
