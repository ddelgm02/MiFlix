package com.fnd.miflix.database

import android.content.Context
import androidx.room.*
import com.fnd.miflix.models.User
import com.fnd.miflix.models.Notificacion
import com.fnd.miflix.models.ContenidoSeguido
import com.fnd.miflix.models.ContentEntity
import com.fnd.miflix.models.DAO.NotificacionesDAO
import com.fnd.miflix.models.DAO.ContenidoSeguidoDao
import com.fnd.miflix.models.DAO.UserDao
import com.fnd.miflix.models.DAO.ContentDao

@Database(entities = [User::class, Notificacion::class, ContenidoSeguido::class, ContentEntity::class], version = 7, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun usuariosDao(): UserDao
    abstract fun notificacionDao(): NotificacionesDAO
    abstract fun contenidoSeguidoDao(): ContenidoSeguidoDao
    abstract fun contentDao(): ContentDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .fallbackToDestructiveMigration() // Esto borra los datos al actualizar
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
