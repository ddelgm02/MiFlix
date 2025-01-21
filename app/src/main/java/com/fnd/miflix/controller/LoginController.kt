package com.fnd.miflix.controller

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginController: ViewModel() {

    // Estado de la UI
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    // Lógica de inicio de sesión
    fun login(email: String, password: String) {
        viewModelScope.launch {
            // Simular una llamada a una API
            _uiState.value = _uiState.value.copy(isLoading = true)

            try {
                // Simulación: Cambia esto por una llamada real a un backend
                if (email == "test@example.com" && password == "password") {
                    _uiState.value = LoginUiState(successMessage = "¡Inicio de sesión exitoso!")
                } else {
                    _uiState.value = LoginUiState(errorMessage = "Credenciales inválidas")
                }
            } catch (e: Exception) {
                _uiState.value = LoginUiState(errorMessage = "Error inesperado: ${e.message}")
            } finally {
                _uiState.value = _uiState.value.copy(isLoading = false)
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
