package com.fnd.miflix.models.DAO

import androidx.room.*
import com.fnd.miflix.models.User

@Dao
interface UserDao {

    // Verificar credenciales para iniciar sesión
    @Query("SELECT * FROM Users WHERE email = :email AND passwordHash = :password")
    suspend fun login(email: String, password: String): User?

    // Buscar usuario por correo
    @Query("SELECT * FROM Users WHERE email = :email")
    suspend fun getUsersByEmail(email: String): User?

    @Insert
    suspend fun register(user: User)

    @Query("SELECT * FROM Users")
    fun getAllUsers(): List<User>

}

