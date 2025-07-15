package com.fhmsyhd.pokemon.di

import com.fhmsyhd.pokemon.core.domain.usecase.PokemonInteractor
import com.fhmsyhd.pokemon.core.domain.usecase.PokemonUseCase
import com.fhmsyhd.pokemon.core.domain.usecase.UserInteractor
import com.fhmsyhd.pokemon.core.domain.usecase.UserUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class AppModule {

    @Binds
    @ViewModelScoped
    abstract fun providePokemonUseCase(pokemonInteractor: PokemonInteractor): PokemonUseCase

    @Binds
    @ViewModelScoped
    abstract fun provideUserUseCase(userInteractor: UserInteractor): UserUseCase
}