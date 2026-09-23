package com.fhmsyhd.pokemon.core.domain.usecase

import com.fhmsyhd.pokemon.core.data.Resource
import com.fhmsyhd.pokemon.core.domain.model.Pokemon
import com.fhmsyhd.pokemon.core.domain.model.PokemonListEntry
import com.fhmsyhd.pokemon.core.domain.repository.IPokemonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PokemonInteractor @Inject constructor(private val pokemonRepository: IPokemonRepository) :
    PokemonUseCase {
    override fun getPokemonList(limit: Int, offset: Int) =
        pokemonRepository.getPokemonList(limit, offset)

    override fun getPokemonInfo(name: String): Flow<Resource<Pokemon>> =
        pokemonRepository.getPokemonInfo(name)

    override fun getFavoritePokemon(): Flow<List<PokemonListEntry>> =
        pokemonRepository.getFavoritePokemon()

    override fun isFavoritePokemon(number: Int): Flow<Boolean> =
        pokemonRepository.isFavoritePokemon(number)

    override suspend fun setFavoritePokemon(pokemon: PokemonListEntry, isFavorite: Boolean) {
        pokemonRepository.setFavoritePokemon(pokemon, isFavorite)
    }
}
