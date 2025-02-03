package com.fnd.miflix.views

import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import androidx.navigation.NavController
import com.fnd.miflix.controller.MoviesController
import com.fnd.miflix.models.ContentEntity
import com.fnd.miflix.models.User
import kotlinx.coroutines.launch


@Composable
fun MovieDetailScreen(movie: ContentEntity, navController: NavController, user: User?, moviesController: MoviesController) {
    Log.e("Añadiendo contenido","He llegado aquí: $movie")
    // Estado para el botón de "Me gusta"
    var isLiked by remember { mutableStateOf(false) }

    // Este LaunchedEffect se ejecutará solo cuando `user` no sea null
    LaunchedEffect(user) {
        // Verifica que `user` no sea nulo antes de ejecutar la lógica
        if (user != null) {
            Log.e("DetailsScreen", "Aqui estoy, el usuario es $user y la peli $movie")

            // Verificar si el contenido es seguido
            val contenidoSeguido = moviesController.isContenidoSeguido(user.id, movie.id)
            Log.i("DetailsScreen", "Es el contenido seguido? $contenidoSeguido")

            // Actualizar el estado de "Me gusta" según si está seguido o no
            isLiked = contenidoSeguido != null
        }
    }

    // Función para manejar el clic en el botón "Me gusta", que será llamada en una corrutina
    suspend fun onLikeButtonClicked() {
        // Asegurarse de que el usuario no sea null
        if (user != null) {
            if (isLiked) {
                // Si está marcado como "Me gusta", eliminarlo de la base de datos
                moviesController.removeContenidoSeguido(user.id, movie.id)
            } else {
                // Si no está marcado, agregarlo a la base de datos
                moviesController.addContenidoSeguido(user, movie)
            }

            // Invertir el estado de "Me gusta"
            isLiked = !isLiked
        } else {
            // Si el usuario es null, se puede mostrar un mensaje o navegar a la pantalla de login
            Log.e("DetailsScreen", "Usuario no encontrado")
        }
    }

    // Crear un alcance de corrutina
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top // Coloca los elementos en la parte superior
        ) {
            // Imagen de la película más grande
            Image(
                painter = rememberImagePainter(
                    data = "https://image.tmdb.org/t/p/w500${movie.posterPath}"
                ),
                contentDescription = movie.title,
                modifier = Modifier
                    .size(300.dp) // Aumentar el tamaño de la imagen
                    .clip(RoundedCornerShape(16.dp))
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Título de la película
            Text(
                text = movie.title,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Descripción de la película
            Text(
                text = movie.overview,
                fontSize = 16.sp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Fecha de estreno y más detalles
            Text(
                text = "Fecha de estreno: ${movie.release_date}",
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Nota media de la audiencia
            Text(
                text = "Nota media de la audiencia: ${movie.vote_average}",
                fontSize = 16.sp
            )
        }

        // Botones abajo
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Botón de "Me gusta"
            IconButton(onClick = {
                // Llamamos a la función en una corrutina
                coroutineScope.launch {
                    onLikeButtonClicked()
                }
            }) {
                Icon(
                    imageVector = if (isLiked) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = "Me gusta",
                    tint = if (isLiked) Color.Red else Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón "Volver"
            Button(onClick = { navController.popBackStack() }) {
                Text("Volver")
            }
        }
    }
}





