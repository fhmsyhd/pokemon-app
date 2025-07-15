package com.fhmsyhd.pokemon.core.domain.usecase

import com.fhmsyhd.pokemon.core.data.repository.UserRepository
import com.fhmsyhd.pokemon.core.domain.model.User
import javax.inject.Inject

class UserInteractor @Inject constructor(private val repository: UserRepository) : UserUseCase {
    override suspend fun register(username: String, password: String) {
        repository.register(username, password)
    }

    override suspend fun login(username: String, password: String): User? {
        return repository.login(username, password)
    }

    override suspend fun getUser(username: String): User? {
        return repository.getUser(username)
    }
}