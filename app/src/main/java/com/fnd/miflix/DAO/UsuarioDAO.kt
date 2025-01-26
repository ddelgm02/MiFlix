package com.fnd.miflix.DAO

import androidx.room.*
import com.fnd.miflix.models.UsuarioConPerfiles

@Dao
interface UsuarioDao {
    //Obtener un usuario con todos sus perfiles
    @Transaction // Necesario para relaciones
    @Query("SELECT * FROM Usuarios WHERE id = :usuarioId")
    suspend fun obtenerUsuarioConPerfiles(usuarioId: Int): UsuarioConPerfiles
}

