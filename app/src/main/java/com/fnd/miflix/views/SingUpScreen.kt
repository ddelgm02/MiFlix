package com.fnd.miflix.views

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fnd.miflix.R
import com.fnd.miflix.controller.SignUpController
import com.fnd.miflix.ui.theme.Purple40
import androidx.navigation.NavController

@Composable
fun SignUpScreen(
    navController: NavController,
    viewModel: SignUpController = viewModel()
) {

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(painter = painterResource(id = R.drawable.applogin), contentDescription = "Sign Up Image", modifier = Modifier.size(300.dp))
        Text(text = "Bienvenido a MiFlix", fontSize = 35.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = "Crear una nueva cuenta")
        Spacer(modifier = Modifier.height(16.dp))

        // Nombre
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text(text = "Nombre") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Email
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Contraseña
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = "Contraseña") },
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Confirmación de Contraseña
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text(text = "Confirmar Contraseña") },
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botón de "Registrarse"
        Button(onClick = {
            if (password.trim() == confirmPassword.trim()) {
                Log.i("Credentials", "Name : $name, Email : $email, Password : $password")
                viewModel.signUp(name, email, password)
            } else {
                Log.e("SignUp", "Las contraseñas no coinciden")
            }
        }) {
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    color = Purple40,
                    modifier = Modifier.size(16.dp)
                )
            } else {
                Text("Registrarse")
            }
        }

        // Mostrar mensajes de éxito o error
        Spacer(modifier = Modifier.height(16.dp))
        uiState.successMessage?.let {
            Text(it, color = Purple40)
        }
        uiState.errorMessage?.let {
            Text(it, color = Purple40)
        }

        // Botón para volver a la pantalla de login
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            navController.popBackStack() // Vuelve a la pantalla de Login
        }) {
            Text(text = "¿Ya tienes una cuenta? Inicia sesión")
        }
    }
}