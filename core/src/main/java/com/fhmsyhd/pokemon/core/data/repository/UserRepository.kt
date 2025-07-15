package com.fhmsyhd.pokemon.core.data.repository

import com.fhmsyhd.pokemon.core.data.local.dao.UserDao
import com.fhmsyhd.pokemon.core.domain.model.User
import com.fhmsyhd.pokemon.core.domain.repository.IUserRepository
import com.fhmsyhd.pokemon.core.util.toDomain
import com.fhmsyhd.pokemon.core.util.toEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userDao: UserDao
) : IUserRepository {

    override suspend fun register(username: String, password: String) {
        val user = User(username, password)
        userDao.insertUser(user.toEntity())
    }

    override suspend fun login(username: String, password: String): User? {
        return userDao.getUser(username)?.let { entity ->
            if (entity.password == password) entity.toDomain()
            else null
        }
    }

    override suspend fun getUser(username: String): User? {
        return userDao.getUser(username)?.toDomain()
    }
}