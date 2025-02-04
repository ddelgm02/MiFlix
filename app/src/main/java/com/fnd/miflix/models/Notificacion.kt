package com.fnd.miflix.models

import androidx.room.*

@Entity(
    tableName = "Notificaciones",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["userId"])]
)
data class Notificacion(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val userId: Int,  // Relacionado con el perfil que recibe la notificación
    val mensaje: String,  // Descripción de la notificación
    val fecha: Long = System.currentTimeMillis(),  // Fecha y hora de la notificación
    val leida: Boolean = false  // Estado de la notificación (leída o no)
)
