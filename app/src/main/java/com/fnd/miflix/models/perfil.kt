package com.fnd.miflix.models

import androidx.room.*

@Entity (
    tableName = "Perfiles",
    foreignKeys = [
        ForeignKey(
            entity = Usuario::class,
            parentColumns = ["id"],
            childColumns = ["usuarioId"],
            onDelete = ForeignKey.CASCADE //Para eliminar todos los perfiles en caso de que se borre el usuario
        )
    ],
    indices = [Index(value = ["usuarioId"])]
)

data class Perfil(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val usuarioId: Int, //Clave foránea con usuario.
    val nombre: String,
    val fechaCreacion: Long = System.currentTimeMillis(),
    val imagenUrl: String? = null
)