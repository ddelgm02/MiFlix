package com.fnd.miflix.models.DAO

import androidx.room.*
import com.fnd.miflix.models.ContenidoSeguido

@Dao
interface ContenidoSeguidoDAO {
    @Query("SELECT contentId FROM ContenidoSeguido WHERE userId = :userId")
    suspend fun getFavoriteContentIds(userId: Int): List<Int>

    @Insert(onConflict = OnConflictStrategy.IGNORE) //Evita que los contenidos se dupliquen
    suspend fun addFavorite(content: ContenidoSeguido)

    @Query("DELETE FROM ContenidoSeguido WHERE userId = :userId AND contentId = :contentId")
    suspend fun removeFavorite(userId: Int, contentId: Int)
}