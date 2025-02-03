package com.fnd.miflix.views

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.fnd.miflix.controller.MoviesController
import com.fnd.miflix.models.ContentEntity
import com.fnd.miflix.models.User
import androidx.compose.foundation.lazy.LazyColumn

@Composable
fun FollowingScreen(
    usuario: User?,
    navController: NavController,
    moviesController: MoviesController
) {
    var followedMovies by remember { mutableStateOf<List<ContentEntity>>(emptyList()) }

    LaunchedEffect(usuario) {
        usuario?.id?.let { userId ->
            followedMovies = moviesController.getAllContenidosSeguidos(userId) //
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Películas que sigues",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (followedMovies.isEmpty()) {
            Text(text = "No sigues ninguna película aún.", color = Color.Gray)
        }

        LazyColumn {
            items(followedMovies.size) { index ->
                MovieItem(movie = followedMovies[index], navController = navController, usuario = usuario!!, moviesController = moviesController)
                Divider(modifier = Modifier.padding(vertical = 8.dp))
            }
        }
    }
}

