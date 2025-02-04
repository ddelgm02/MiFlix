package com.example.app

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
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.fnd.miflix.controller.AdminController
import com.fnd.miflix.models.User
import kotlinx.coroutines.launch

@Composable
fun AdminScreen(
    navController: NavController,
    adminController: AdminController = viewModel()
) {
    val users by adminController.users.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        adminController.fetchAllUsers()
    }


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

        users.forEach { user ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(user.name, modifier = Modifier.weight(1f))
                Text(if (user.admin) "Admin" else "Usuario")
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = {
                    coroutineScope.launch {
                        adminController.actualizarRol(user.email, !user.admin)
                    }
                }) {
                    Text(if (user.admin) "Revertir a Usuario" else "Hacer Admin")
                }
            }
            Divider(modifier = Modifier.padding(vertical = 8.dp))
        }
    }
}


