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
import com.fnd.miflix.R
import com.fnd.miflix.controller.LoginController
import com.fnd.miflix.ui.theme.Purple40


@Composable
fun LoginScreen(viewModel: LoginController = LoginController()){

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Image(painter = painterResource(id = R.drawable.mobile_login), contentDescription = "Login Image", modifier = Modifier.size(300.dp))
        Text(text = "Bienvenido a MiFlix", fontSize = 35.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = "Iniciar Sesión en tu cuenta")
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = password, onValueChange = {
            password = it
        }, label = {
            Text(text = "Password")
        }, visualTransformation = PasswordVisualTransformation())
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            Log.i("Credentials", "Email : $email  Password : $password")
            viewModel.login(email, password)
        }) {
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    color = Purple40,
                    modifier = Modifier.size(16.dp)
                )
            } else {
                Text("Iniciar sesión")
            }

        }

        // Mostrar mensajes de éxito o error
        uiState.successMessage?.let {
            Spacer(modifier = Modifier.height(16.dp))
            Text(it, color = Purple40)
        }
        uiState.errorMessage?.let {
            Spacer(modifier = Modifier.height(16.dp))
            Text(it, color = Purple40)
        }
    }

}

