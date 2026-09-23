package com.fhmsyhd.pokemon.testutil

import com.fhmsyhd.pokemon.core.data.Resource
import com.fhmsyhd.pokemon.core.domain.model.Pokemon
import com.fhmsyhd.pokemon.core.domain.model.PokemonListEntry
import com.fhmsyhd.pokemon.core.domain.usecase.PokemonUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emptyFlow
import java.util.ArrayDeque

class FakePokemonUseCase : PokemonUseCase {
    val pokemonListResponses = ArrayDeque<Flow<Resource<List<PokemonListEntry>>>>()
    var pokemonInfoResponse: Flow<Resource<Pokemon>> = emptyFlow()
    val favorites = MutableStateFlow<List<PokemonListEntry>>(emptyList())
    val favoriteState = MutableStateFlow(false)

    var listRequestCount = 0
        private set
    var lastUpdatedPokemon: PokemonListEntry? = null
        private set
    var lastFavoriteValue: Boolean? = null
        private set

    override fun getPokemonList(
        limit: Int,
        offset: Int
    ): Flow<Resource<List<PokemonListEntry>>> {
        listRequestCount++
        return if (pokemonListResponses.isEmpty()) {
            emptyFlow()
        } else {
            pokemonListResponses.removeFirst()
        }
    }

    override fun getPokemonInfo(name: String): Flow<Resource<Pokemon>> = pokemonInfoResponse

    override fun getFavoritePokemon(): Flow<List<PokemonListEntry>> = favorites

    override fun isFavoritePokemon(number: Int): Flow<Boolean> = favoriteState

    override suspend fun setFavoritePokemon(
        pokemon: PokemonListEntry,
        isFavorite: Boolean
    ) {
        lastUpdatedPokemon = pokemon
        lastFavoriteValue = isFavorite
        favoriteState.value = isFavorite
        favorites.value = if (isFavorite) {
            (favorites.value + pokemon).distinctBy { it.number }
        } else {
            favorites.value.filterNot { it.number == pokemon.number }
        }
    }
}
