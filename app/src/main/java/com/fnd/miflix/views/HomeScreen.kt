package com.fnd.miflix.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.fnd.miflix.controller.MoviesController
import com.fnd.miflix.models.Movie
import androidx.compose.runtime.remember
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.ui.graphics.Color


@Composable
fun HomeScreen(moviesController: MoviesController = viewModel()) {
    val moviesList by moviesController.movies.observeAsState(initial = emptyList())
    LaunchedEffect(Unit) {
        moviesController.fetchPopularMovies()
    }

    var searchQuery by remember { mutableStateOf("") }

    // Filtramos la lista solo cuando cambia la búsqueda
    val filteredMovies = moviesList.filter {
        it.title.contains(searchQuery, ignoreCase = true) ||
                it.overview.contains(searchQuery, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Barra de navegación personalizada usando Row
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

            // Icono de búsqueda
            IconButton(onClick = { /* Aquí puedes agregar la lógica de búsqueda */ }) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Buscar"
                )
            }
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

        // LazyColumn para mostrar la lista de películas filtrada
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            // Aseguramos que el índice nunca se salga del tamaño de la lista filtrada
            items(filteredMovies.size) { index ->
                MovieItem(movie = filteredMovies[index])
                Divider(modifier = Modifier.padding(vertical = 8.dp))
            }
        }
    }
}



@Composable
fun MovieItem(movie: Movie) {
    var isLiked by remember { mutableStateOf(false) } // Estado del "me gusta"

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

        // Información de la película
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = movie.title,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
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
            onClick = { isLiked = !isLiked } // Cambia el estado al hacer clic
        ) {
            Icon(
                imageVector = if (isLiked) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                contentDescription = if (isLiked) "Quitar de favoritos" else "Añadir a favoritos",
                tint = if (isLiked) Color.Red else Color.Gray // Cambia el color
            )
        }
    }
}
