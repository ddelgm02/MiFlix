package com.fnd.miflix.views

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.fnd.miflix.controller.MoviesController
import androidx.compose.runtime.remember
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.fnd.miflix.models.ContentEntity
import com.fnd.miflix.models.User
import kotlinx.coroutines.launch


@Composable
fun HomeScreen(
    usuario: User,
    navController: NavController,
    moviesList: List<ContentEntity>,
    moviesController: MoviesController
) {
    var searchQuery by remember { mutableStateOf("") }

    val filteredMovies = moviesList.filter {
        it.title.contains(searchQuery, ignoreCase = true) ||
                it.overview.contains(searchQuery, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Barra de navegación
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Título
            Text(
                text = "MiFlix",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navController.navigate("following/${usuario.email}") }) {
            Text("Mis Películas Seguidas")
        }

        Spacer(modifier = Modifier.height(16.dp))
        // Barra de búsqueda
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { query -> searchQuery = query },
            label = { Text("Buscar película o serie") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Verificamos si hay películas en la lista filtrada
        if (filteredMovies.isEmpty()) {
            Text(text = "No se encontraron películas.", color = Color.Gray)
        }

        // LazyColumn para mostrar la lista de películas filtrada
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(filteredMovies.size) { index ->
                MovieItem(movie = filteredMovies[index], navController = navController, usuario = usuario, moviesController = moviesController)
                Divider(modifier = Modifier.padding(vertical = 8.dp))
            }
        }

        if (usuario.admin) {
            Button(onClick = { navController.navigate("admin") }) {
                Text("Gestionar Usuarios")
            }
        }
    }
}


@Composable
fun MovieItem(
    movie: ContentEntity,
    navController: NavController,
    usuario: User,
    moviesController: MoviesController // Asegúrate de pasar el controlador
) {
    var isLiked by remember { mutableStateOf(false) }

    LaunchedEffect(movie.id, usuario.id) {
        val contenidoSeguido = moviesController.isContenidoSeguido(usuario.id, movie.id)
        isLiked = contenidoSeguido != null // Si el contenido está en la base de datos, marcamos el "Me gusta"
    }

    val scope = rememberCoroutineScope() // Usar rememberCoroutineScope para obtener un scope

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Imagen de la película
        Image(
            painter = rememberImagePainter(
                data = "https://image.tmdb.org/t/p/w500${movie.posterPath}"
            ),
            contentDescription = movie.title,
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(8.dp))
        )
        Spacer(modifier = Modifier.width(8.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            // Título de la película con acción de navegación
            Text(
                text = movie.title,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.clickable {
                    // Navegar a la pantalla de detalles
                    navController.navigate("movie_details/${movie.id}?email=${usuario.email}")
                }
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = movie.overview,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }

        // Botón de "Me gusta"
        IconButton(
            onClick = {
                scope.launch {
                    // Cambiar el estado de "Me gusta" y actualizar la base de datos
                    if (isLiked) {
                        // Si está en favoritos, eliminarlo
                        moviesController.removeContenidoSeguido(usuario.id, movie.id)
                    } else {
                        // Si no está en favoritos, agregarlo
                        moviesController.addContenidoSeguido(usuario, movie)
                    }
                    // Invertir el estado del "Me gusta"
                    isLiked = !isLiked
                }
            }
        ) {
            Icon(
                imageVector = if (isLiked) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                contentDescription = if (isLiked) "Quitar de favoritos" else "Añadir a favoritos",
                tint = if (isLiked) Color.Red else Color.Gray // Cambia el color
            )
        }
    }
}





/*@Composable
fun HomeScreen(loginController: LoginController, usuario: User){
    var esAdmin by remember { mutableStateOf(usuario.admin) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Bienvenido, ${usuario.name}!", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = if (esAdmin) "Estás en modo ADMINISTRADOR" else "Estás en modo USUARIO",
            fontSize = 18.sp,
            color = if (esAdmin) Color.Red else Color.Blue
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            loginController.cambiarRol(usuario)
            esAdmin = usuario.admin // Actualizar la UI
        }) {
            Text(if (esAdmin) "Cambiar a Modo Usuario" else "Cambiar a Modo Admin")
        }
    }
}*/