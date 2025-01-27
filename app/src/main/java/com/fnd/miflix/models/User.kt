package com.fnd.miflix.models

import androidx.room.*

// Entidad Usuarios
@Entity (tableName = "Usuarios",
        indices = [Index(value = ["correo"], unique = true)]
)
data class Usuario(
    @PrimaryKey (autoGenerate = true) val id: Int,
    val nombre: String,
    val correo: String,
    val fechaCreacion: Long = System.currentTimeMillis(),
    val admin: Boolean = false,
    val password: String
)