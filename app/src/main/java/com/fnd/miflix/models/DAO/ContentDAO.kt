package com.fnd.miflix.models.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.fnd.miflix.models.ContentEntity

@Dao
interface ContentDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE) // Evita sobrescribir contenido existente
    suspend fun insertContent(content: ContentEntity)

    // Obtener contenido por ID
    @Query("SELECT * FROM content WHERE id = :id")
    suspend fun getContentById(id: Int): ContentEntity?

    // Obtener todos los contenidos
    @Query("SELECT * FROM content")
    suspend fun getAllContent(): List<ContentEntity>

    @Update
    suspend fun updateContent(content: ContentEntity)

    @Delete
    suspend fun deleteContent(content: ContentEntity)

}
