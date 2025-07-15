package com.fhmsyhd.pokemon.core.domain.repository

import com.fhmsyhd.pokemon.core.data.Resource
import com.fhmsyhd.pokemon.core.domain.model.Pokemon
import com.fhmsyhd.pokemon.core.domain.model.PokemonListEntry
import kotlinx.coroutines.flow.Flow

interface IPokemonRepository {
    fun getPokemonList(limit: Int, offset: Int): Flow<Resource<List<PokemonListEntry>>>

    fun getPokemonInfo(name: String): Flow<Resource<Pokemon>>
}