package com.fnd.miflix.models

import androidx.room.*

@Entity(
    tableName = "ContenidoSeguido",
    foreignKeys = [
        ForeignKey(
            entity = Perfil::class,
            parentColumns = ["id"],
            childColumns = ["perfilId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["perfilId"])],
    primaryKeys = ["perfilId", "contenidoId"]
)
data class ContenidoSeguido(
    val perfilId: Int,
    val contenidoId: Int      // ID del contenido seguido (referencia al catálogo de la API)
)