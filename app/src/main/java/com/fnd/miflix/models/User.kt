package com.fnd.miflix.models

import androidx.room.*
import com.fnd.miflix.models.Perfil

// Entidad Usuarios
@Entity (tableName = "Usuarios")
data class Usuario(
    @PrimaryKey val id: Int,
    val nombre: String,
    val correo: String
)