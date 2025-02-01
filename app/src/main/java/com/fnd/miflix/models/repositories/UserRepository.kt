package com.fnd.miflix.models.repositories

import com.fnd.miflix.models.DAO.UserDao
import com.fnd.miflix.models.User

class UserRepository(private val userDao: UserDao) {
    suspend fun login(email: String, password: String): User? {
        return userDao.login(email, password)
    }

    suspend fun register(user: User) {
        userDao.register(user)
    }


}