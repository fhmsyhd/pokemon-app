package com.fhmsyhd.pokemon.core.domain.repository

import com.fhmsyhd.pokemon.core.domain.model.User

interface IUserRepository {
    suspend fun register(username: String, password: String)
    suspend fun login(username: String, password: String): User?
    suspend fun getUser(username: String): User?
}