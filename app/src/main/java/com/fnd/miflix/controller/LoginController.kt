package com.fnd.miflix.controller

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.fnd.miflix.models.DAO.UserDao
import org.mindrot.jbcrypt.BCrypt
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.fnd.miflix.database.AppDatabase
import com.fnd.miflix.models.User

class LoginController(application: Application) : AndroidViewModel(application){

    private val userDao: UserDao = AppDatabase.getDatabase(application).usuariosDao()

    // Estado de la UI
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    // Lógica de inicio de sesión con RoomDB
    fun login(email: String, password: String, onLoginSuccess: (User) -> Unit) {

        viewModelScope.launch {
            _uiState.value = LoginUiState(isLoading = true)

            try {
                val usuario = userDao.getUsersByEmail(email)

                if (usuario != null && BCrypt.checkpw(password.trim(), usuario.passwordHash)) {
                    _uiState.value = LoginUiState(
                        successMessage = "¡Inicio de sesión exitoso!",
                    )
                    onLoginSuccess(usuario)
                } else {
                    _uiState.value = LoginUiState(errorMessage = "Correo o contraseña inválidas")
                }
            } catch (e: Exception) {
                _uiState.value = LoginUiState(errorMessage = "Error inesperado: ${e.message}")
            } finally {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }

    //Cambiar a rol Admin
    fun cambiarRol(usuario: User) {
        usuario.admin = !usuario.admin // Alterna entre usuario y admin
    }
}

// Clase que define el estado de la pantalla
data class LoginUiState(
    val isLoading: Boolean = false,
    val successMessage: String? = null,
    val errorMessage: String? = null
)
