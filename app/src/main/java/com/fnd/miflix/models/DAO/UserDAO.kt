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
    suspend fun insertUser(user: User)

    @Transaction
    suspend fun register(user: User) {
        val isEmpty = isDatabaseEmpty() == 0
        user.admin = isEmpty  // Si es el primer usuario, se le asigna admin = true
        insertUser(user)
    }

    @Query("SELECT * FROM Users")
    suspend fun getAllUsers(): List<User>

    @Query("UPDATE Users SET admin = :esAdmin WHERE email = :email")
    suspend fun actualizarRol(email: String, esAdmin: Boolean)

    @Query("SELECT * FROM Users WHERE id = :id")
    suspend fun getUserById(id: Int): User?

    // Actualizar la información del usuario (si fuera necesario)
    @Update
    suspend fun updateUser(user: User)

    // Eliminar un usuario
    @Delete
    suspend fun deleteUser(user: User)

    @Query("SELECT COUNT(*) FROM Users")
    suspend fun isDatabaseEmpty(): Int

}

