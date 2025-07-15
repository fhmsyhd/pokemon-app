package com.fhmsyhd.pokemon.core.domain.usecase

import com.fhmsyhd.pokemon.core.domain.model.User

interface UserUseCase {
    suspend fun register(username: String, password: String)
    suspend fun login(username: String, password: String): User?
    suspend fun getUser(username: String): User?
}