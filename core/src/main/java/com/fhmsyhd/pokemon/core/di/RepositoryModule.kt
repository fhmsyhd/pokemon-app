package com.fhmsyhd.pokemon.core.di

import com.fhmsyhd.pokemon.core.data.repository.PokemonRepository
import com.fhmsyhd.pokemon.core.data.repository.UserRepository
import com.fhmsyhd.pokemon.core.domain.repository.IPokemonRepository
import com.fhmsyhd.pokemon.core.domain.repository.IUserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindPokemonRepository(
        pokemonRepository: PokemonRepository
    ): IPokemonRepository

    @Binds
    abstract fun bindUserRepository(
        userRepository: UserRepository
    ): IUserRepository
}