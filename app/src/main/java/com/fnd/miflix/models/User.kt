package com.fnd.miflix.models

import androidx.room.*
import org.mindrot.jbcrypt.BCrypt

// Entidad Usuarios
@Entity (tableName = "Usuarios",
        indices = [Index(value = ["correo"], unique = true)]
)
data class Usuario(
    @PrimaryKey (autoGenerate = true) val id: Int,
    val nombre: String,
    val correo: String,
    val passwordHash: String,
    val fechaCreacion: Long = System.currentTimeMillis(),
    val admin: Boolean = false
)
{
    // Método para verificar la contraseña
    fun verificarPassword(password: String): Boolean {
        return BCrypt.checkpw(password, passwordHash)
    }

    companion object {
        // Método para cifrar una contraseña antes de guardarla en la base de datos
        fun hashPassword(password: String): String {
            return BCrypt.hashpw(password, BCrypt.gensalt())
        }
    }
}