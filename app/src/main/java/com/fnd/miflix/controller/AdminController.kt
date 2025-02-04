package com.fnd.miflix.controller

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.fnd.miflix.database.AppDatabase
import com.fnd.miflix.models.ContentEntity
import com.fnd.miflix.models.DAO.ContentDao
import com.fnd.miflix.models.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AdminController (application: Application) : AndroidViewModel(application) {
    private val contenidoSeguidoDao = AppDatabase.getDatabase(application).contenidoSeguidoDao()
    private val userDao = AppDatabase.getDatabase(application).usuariosDao()
    private val contentDao = AppDatabase.getDatabase(application).contentDao()

    private val _users = MutableStateFlow<List<User>>(emptyList())
    private val _contents = MutableStateFlow<List<ContentEntity>>(emptyList())

    val users: StateFlow<List<User>> = _users.asStateFlow()
    val contents: StateFlow<List<ContentEntity>> = _contents.asStateFlow()

    fun fetchAllUsers() {
        viewModelScope.launch {
            _users.value = userDao.getAllUsers()
        }
    }

    fun actualizarRol(email: String, esAdmin: Boolean) {
        viewModelScope.launch {
            userDao.actualizarRol(email, esAdmin)
            fetchAllUsers()
        }
    }

    fun fetchAllcontent(){
        viewModelScope.launch {
            _contents.value = contentDao.getAllContent()
        }
    }

    fun deleteContent(contentId: Int) {
        viewModelScope.launch {
            contentDao.deleteContentById(contentId)
            fetchAllcontent()
        }
    }

    fun updateContent(content: ContentEntity) {
        viewModelScope.launch {
            contentDao.updateContent(content)
            fetchAllcontent()
        }
    }


}