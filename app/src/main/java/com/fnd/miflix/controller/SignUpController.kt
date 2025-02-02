package com.fnd.miflix.controller

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.fnd.miflix.database.AppDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.mindrot.jbcrypt.BCrypt
import com.fnd.miflix.models.User

class SignUpController(application: Application) : AndroidViewModel(application) {

    private val usuarioDao = AppDatabase.getDatabase(application).usuariosDao()

    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = _uiState.asStateFlow()

    fun signUp(name: String, email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            try {
                // Verificar si el usuario ya existe
                val existingUser = usuarioDao.getUsersByEmail(email)

                if (existingUser != null) {
                    _uiState.value = SignUpUiState(errorMessage = "El usuario ya existe")
                } else {
                    // Crear el nuevo usuario
                    val hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt())
                    val newUser = User(
                        id = 0,
                        name = name,
                        email = email,
                        passwordHash = hashedPassword,
                        admin = false
                    )

                    // Guardar el usuario en la base de datos
                    usuarioDao.register(newUser)

                    _uiState.value = SignUpUiState(successMessage = "¡Registro exitoso!")
                }
            } catch (e: Exception) {
                _uiState.value = SignUpUiState(errorMessage = "Error inesperado: ${e.message}")
            } finally {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }
}

// Clase para manejar el estado de la UI en SignUp
data class SignUpUiState(
    val isLoading: Boolean = false,
    val successMessage: String? = null,
    val errorMessage: String? = null
)
