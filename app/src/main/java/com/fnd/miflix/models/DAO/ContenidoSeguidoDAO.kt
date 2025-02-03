package com.fnd.miflix.models.DAO

import androidx.room.*
import com.fnd.miflix.models.ContenidoSeguido

@Dao
interface ContenidoSeguidoDao {

    // Insertar un nuevo contenido seguido
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContenidoSeguido(contenidoSeguido: ContenidoSeguido)

    // Comprobar si un contenido ya está seguido por el usuario
    @Query("SELECT * FROM ContenidoSeguido WHERE userId = :userId AND contentId = :contentId")
    suspend fun isContenidoSeguido(userId: Int, contentId: Int): ContenidoSeguido?

    // Eliminar un contenido seguido (si el usuario lo quita de sus favoritos)
    @Query("DELETE FROM ContenidoSeguido WHERE userId = :userId AND contentId = :contentId")
    suspend fun removeContenidoSeguido(userId: Int, contentId: Int)

    // Obtener todos los contenidos seguidos de un usuario
    @Query("SELECT * FROM ContenidoSeguido WHERE userId = :userId")
    suspend fun getAllContenidosSeguidos(userId: Int): List<ContenidoSeguido>
}
