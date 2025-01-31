package com.fnd.miflix.controller

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.fnd.miflix.DAO.UsuarioDao
import org.mindrot.jbcrypt.BCrypt
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.fnd.miflix.database.AppDatabase

class LoginController(application: Application) : AndroidViewModel(application){

    private val usuarioDao = AppDatabase.getDatabase(application).usuariosDao()

    // Estado de la UI
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    // Lógica de inicio de sesión con RoomDB
    fun login(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = LoginUiState(isLoading = true)

            try {
                // Simulación: Cambia esto por una llamada real a un backend
                val usuario = usuarioDao.obtenerUsuarioPorCorreo(email)

                if (usuario != null && BCrypt.checkpw(password.trim(), usuario.passwordHash)) {
                    _uiState.value = LoginUiState(successMessage = "¡Inicio de sesión exitoso!")
                } else {
                    _uiState.value = LoginUiState(errorMessage = "Correo o contraseña inválidas")
                }
            } catch (e: Exception) {
                _uiState.value = LoginUiState(errorMessage = "Error inesperado: ${e.message}")
            } finally {
                _uiState.value = LoginUiState(isLoading = false)
            }
        }
    }
}

// Clase que define el estado de la pantalla
data class LoginUiState(
    val isLoading: Boolean = false,
    val successMessage: String? = null,
    val errorMessage: String? = null
)
