package com.fnd.miflix.database

import android.content.Context
import androidx.room.*

import com.fnd.miflix.models.User
import com.fnd.miflix.models.Notificacion
import com.fnd.miflix.models.ContenidoSeguido
import com.fnd.miflix.models.DAO.NotificacionesDAO
import com.fnd.miflix.models.DAO.ContenidoSeguidoDAO
import com.fnd.miflix.models.DAO.UserDao

// Define la base de datos y las entidades que usa
@Database(entities = [User::class, Notificacion::class, ContenidoSeguido::class], version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    // Métodos abstractos para acceder a los DAOs
    abstract fun usuariosDao(): UserDao
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
