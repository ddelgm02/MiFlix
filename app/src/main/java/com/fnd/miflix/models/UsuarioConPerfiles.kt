package com.fnd.miflix.models

import androidx.room.*

data class UsuarioConPerfiles(
    @Embedded val usuario: Usuario,
    @Relation(
        parentColumn = "id",
        entityColumn = "usuarioId"
    )
    val perfiles: List<Perfil> // Lista de perfiles relacionados
)