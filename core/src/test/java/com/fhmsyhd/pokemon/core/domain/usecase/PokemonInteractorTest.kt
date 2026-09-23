package com.fhmsyhd.pokemon.core.domain.usecase

import com.fhmsyhd.pokemon.core.data.Resource
import com.fhmsyhd.pokemon.core.domain.model.Pokemon
import com.fhmsyhd.pokemon.core.domain.model.PokemonListEntry
import com.fhmsyhd.pokemon.core.domain.repository.IPokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test

class PokemonInteractorTest {

    private val repository = FakePokemonRepository()
    private val interactor = PokemonInteractor(repository)

    @Test
    fun `favorites flow is exposed from repository`() = runBlocking {
        val pokemon = PokemonListEntry("PIKACHU", "pikachu.png", 25)
        repository.favorites = listOf(pokemon)

        assertEquals(listOf(pokemon), interactor.getFavoritePokemon().first())
    }

    @Test
    fun `removing favorite delegates expected state`() = runBlocking {
        val pokemon = PokemonListEntry("PIKACHU", "pikachu.png", 25)

        interactor.setFavoritePokemon(pokemon, isFavorite = false)

        assertEquals(pokemon, repository.updatedPokemon)
        assertFalse(repository.updatedFavoriteState)
    }
}

private class FakePokemonRepository : IPokemonRepository {
    var favorites: List<PokemonListEntry> = emptyList()
    var updatedPokemon: PokemonListEntry? = null
    var updatedFavoriteState = true

    override fun getPokemonList(
        limit: Int,
        offset: Int
    ): Flow<Resource<List<PokemonListEntry>>> = emptyFlow()

    override fun getPokemonInfo(name: String): Flow<Resource<Pokemon>> = emptyFlow()

    override fun getFavoritePokemon(): Flow<List<PokemonListEntry>> = flowOf(favorites)

    override fun isFavoritePokemon(number: Int): Flow<Boolean> = flowOf(false)

    override suspend fun setFavoritePokemon(
        pokemon: PokemonListEntry,
        isFavorite: Boolean
    ) {
        updatedPokemon = pokemon
        updatedFavoriteState = isFavorite
    }
}
