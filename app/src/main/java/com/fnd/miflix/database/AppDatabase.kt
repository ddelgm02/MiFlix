package com.fnd.miflix.database

import android.content.Context
import androidx.room.*

import com.fnd.miflix.models.Usuario
import com.fnd.miflix.models.Perfil
import com.fnd.miflix.models.Notificacion
import com.fnd.miflix.models.ContenidoSeguido
import com.fnd.miflix.DAO.UsuarioDao
import com.fnd.miflix.DAO.PerfilDAO
import com.fnd.miflix.DAO.NotificacionesDAO
import com.fnd.miflix.DAO.ContenidoSeguidoDAO

// Define la base de datos y las entidades que usa
@Database(entities = [Usuario::class, Perfil::class, Notificacion::class, ContenidoSeguido::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    // Métodos abstractos para acceder a los DAOs
    abstract fun usuariosDao(): UsuarioDao
    abstract fun perfilDao(): PerfilDAO
    abstract fun notificacionDao(): NotificacionesDAO
    abstract fun contenidoSeguidoDao(): ContenidoSeguidoDAO

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        // Patrón Singleton para obtener la instancia de la base de datos
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"  // Nombre de la base de datos
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
