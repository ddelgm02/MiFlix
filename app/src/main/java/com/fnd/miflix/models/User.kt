package com.fnd.miflix.models

import androidx.room.*
import org.mindrot.jbcrypt.BCrypt

// Entidad Usuarios
@Entity (tableName = "Users",
        indices = [Index(value = ["email"], unique = true)]
)
data class User(
    @PrimaryKey (autoGenerate = true) val id: Int,
    val name: String,
    val email: String,
    val passwordHash: String,
    val creationDate: Long = System.currentTimeMillis(),
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