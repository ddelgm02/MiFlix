package com.fnd.miflix.DAO

import androidx.room.*
import com.fnd.miflix.models.UsuarioConPerfiles
import com.fnd.miflix.models.Usuario

@Dao
interface UsuarioDao {
    //Obtener un usuario con todos sus perfiles
    @Transaction // Necesario para relaciones
    @Query("SELECT * FROM Usuarios WHERE id = :usuarioId")
    suspend fun obtenerUsuarioConPerfiles(usuarioId: Int): UsuarioConPerfiles

    // Verificar credenciales para iniciar sesión
    @Query("SELECT * FROM Usuarios WHERE correo = :correo AND passwordHash = :password")
    suspend fun iniciarSesion(correo: String, password: String): Usuario?

    // Buscar usuario por correo
    @Query("SELECT * FROM Usuarios WHERE correo = :correo")
    suspend fun obtenerUsuarioPorCorreo(correo: String): Usuario?

    @Insert
    suspend fun insertarUsuario(usuario: Usuario)

    @Query("SELECT * FROM Usuarios")
    fun obtenerTodosUsuarios(): List<Usuario>
}

