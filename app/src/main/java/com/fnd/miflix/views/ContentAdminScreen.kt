package com.fnd.miflix.views

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.fnd.miflix.controller.AdminController
import com.fnd.miflix.models.Content
import com.fnd.miflix.models.ContentEntity
import kotlinx.coroutines.launch

@Composable
fun ContentAdminScreen(
    navController: NavController,
    adminController: AdminController = viewModel()
) {
    val contents by adminController.contents.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    var showEditForm by remember { mutableStateOf(false) }
    var selectedContent by remember { mutableStateOf<ContentEntity?>(null) }

    // Obtener contenido al cargar la pantalla
    LaunchedEffect(Unit) {
        adminController.fetchAllcontent()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Administrar Contenido", fontSize = 24.sp)

        Spacer(modifier = Modifier.height(16.dp))

        // Mostrar lista de contenidos
        contents.forEach { content ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(content.title, modifier = Modifier.weight(1f))

                Button(onClick = {
                    selectedContent = content
                    showEditForm = true
                }) {
                    Text("Editar")
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(onClick = {
                    coroutineScope.launch {
                        adminController.deleteContent(content.id)
                    }
                }) {
                    Text("Eliminar")
                }
            }
            Divider(modifier = Modifier.padding(vertical = 8.dp))
        }

        // Formulario de edición si se selecciona un contenido
        if (showEditForm && selectedContent != null) {
            EditContentForm(
                content = selectedContent!!,
                onSave = { updatedContent ->
                    coroutineScope.launch {
                        adminController.updateContent(updatedContent)
                    }
                    showEditForm = false
                },
                onCancel = {
                    showEditForm = false
                }
            )
        }
    }
}

@Composable
fun EditContentForm(
    content: ContentEntity,
    onSave: (ContentEntity) -> Unit,
    onCancel: () -> Unit
) {
    var title by remember { mutableStateOf(content.title) }
    var overview by remember { mutableStateOf(content.overview) }
    var posterPath by remember { mutableStateOf(content.posterPath) }
    var releaseDate by remember { mutableStateOf(content.release_date) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Editar Contenido", fontSize = 24.sp)

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Título") }
        )
        TextField(
            value = overview,
            onValueChange = { overview = it },
            label = { Text("Descripción") }
        )
        TextField(
            value = posterPath,
            onValueChange = { posterPath = it },
            label = { Text("Ruta del Poster") }
        )
        TextField(
            value = releaseDate,
            onValueChange = { releaseDate = it },
            label = { Text("Fecha de Lanzamiento") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            val updatedContent = content.copy(
                title = title,
                overview = overview,
                posterPath = posterPath,
                release_date = releaseDate
            )
            onSave(updatedContent)
        }) {
            Text("Guardar Cambios")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = onCancel) {
            Text("Cancelar")
        }
    }
}
