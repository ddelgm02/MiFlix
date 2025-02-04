@file:Suppress("UNREACHABLE_CODE")

package com.fnd.miflix.views

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.app.AdminScreen
import com.fnd.miflix.models.Content
import com.fnd.miflix.models.ContentEntity
import com.fnd.miflix.models.DAO.ContentDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun ContentAdminScreen(navController: NavHostController) {
    // Simulamos una lista de contenido
    val contentList = remember { mutableStateOf(mutableListOf<Content>()) }
    val showEditForm = remember { mutableStateOf(false) }
    val selectedContent = remember { mutableStateOf<Content.Movie?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Administrar Contenido", fontSize = 24.sp)

        Spacer(modifier = Modifier.height(16.dp))

        // Mostrar el contenido
        contentList.value.forEach { content ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(content.title, modifier = Modifier.weight(1f))
                Button(onClick = {
                    //selectedContent.value = content
                    showEditForm.value = true
                }) {
                    Text("Editar")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        /*ContentDao.deleteContent(ContentEntity.fromMovie(content)) // Convertir a ContentEntity
                        // Actualizar la lista de contenido en el hilo principal
                        contentList.value = contentList.value.filter { it.id != content.id }*/
                    }
                    contentList.value.remove(content)
                }) {
                    Text("Eliminar")
                }
            }
            Divider(modifier = Modifier.padding(vertical = 8.dp))
        }

        if (showEditForm.value && selectedContent.value != null) {
            EditContentForm(
                content = selectedContent.value!!,
                onSave = { updatedContent ->
                    // Guardar el contenido actualizado en la base de datos
                    CoroutineScope(Dispatchers.IO).launch {
                        /*ContentDao.updateContent(ContentEntity.fromMovie(updatedContent))
                        contentList.value = contentList.value.map {
                            if (it.id == updatedContent.id) updatedContent else it
                        }*/
                    }
                    showEditForm.value = false // Cerrar el formulario
                },
                onCancel = {
                    showEditForm.value = false // Cerrar el formulario sin guardar
                }
            )
        }
    }
}

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                navController = navController,
                usuario = TODO(),
                moviesController = TODO(),
                moviesList = TODO()
            )
        }
        composable("admin") {
            AdminScreen(navController = navController)
        }
        composable("contentAdmin") {
            ContentAdminScreen(navController = navController)
        }
    }
}

@Composable
fun EditContentForm(
    content: Content.Movie,
    onSave: (Content.Movie) -> Unit,
    onCancel: () -> Unit
) {
    val title = remember { mutableStateOf(content.title) }
    val overview = remember { mutableStateOf(content.overview) }
    val posterPath = remember { mutableStateOf(content.posterPath) }
    val releaseDate = remember { mutableStateOf(content.releaseDate) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Editar Contenido", fontSize = 24.sp)

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = title.value,
            onValueChange = { title.value = it },
            label = { Text("Título") }
        )
        TextField(
            value = overview.value,
            onValueChange = { overview.value = it },
            label = { Text("Descripción") }
        )
        TextField(
            value = posterPath.value,
            onValueChange = { posterPath.value = it },
            label = { Text("Ruta del Poster") }
        )
        TextField(
            value = releaseDate.value,
            onValueChange = { releaseDate.value = it },
            label = { Text("Fecha de Lanzamiento") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            // Crear un nuevo objeto Content.Movie con los datos editados
            val updatedContent = Content.Movie(
                id = content.id, // Mantener el mismo ID
                title = title.value,
                overview = overview.value,
                posterPath = posterPath.value,
                releaseDate = releaseDate.value,
                runtime = TODO() // Manejar la conversión
            )
            onSave(updatedContent) // Llamar a la función de guardado
        }) {
            Text("Guardar Cambios")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = onCancel) {
            Text("Cancelar")
        }
    }
}