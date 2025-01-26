package com.fnd.miflix.models

import androidx.room.*

@Entity(
    tableName = "Notificaciones",
    foreignKeys = [
        ForeignKey(
            entity = Perfil::class,
            parentColumns = ["id"],
            childColumns = ["perfilId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["perfilId"])]
)
data class Notificacion(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val perfilId: Int,  // Relacionado con el perfil que recibe la notificación
    val mensaje: String,  // Descripción de la notificación
    val fecha: Long = System.currentTimeMillis(),  // Fecha y hora de la notificación
    val leida: Boolean = false  // Estado de la notificación (leída o no)
)
