package com.fhmsyhd.pokemon.ui.pokemondetail

import com.fhmsyhd.pokemon.core.data.Resource
import com.fhmsyhd.pokemon.core.domain.model.Pokemon
import com.fhmsyhd.pokemon.core.domain.model.Stat
import com.fhmsyhd.pokemon.core.domain.model.Type
import com.fhmsyhd.pokemon.testutil.FakePokemonUseCase
import com.fhmsyhd.pokemon.testutil.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PokemonDetailViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val pikachu = Pokemon(
        id = 25,
        name = "pikachu",
        imageUrl = "pikachu.png",
        types = listOf(Type("electric")),
        weight = 60,
        height = 4,
        stats = listOf(Stat("speed", 90))
    )

    @Test
    fun `successful load exposes pokemon and observes favorite state`() = runTest {
        val useCase = FakePokemonUseCase().apply {
            pokemonInfoResponse = flowOf(Resource.Loading(), Resource.Success(pikachu))
            favoriteState.value = true
        }
        val viewModel = PokemonDetailViewModel(useCase)

        viewModel.loadPokemonInfo("pikachu")
        advanceUntilIdle()

        val result = viewModel.pokemonInfo.value as Resource.Success
        assertEquals(pikachu, result.data)
        assertTrue(viewModel.isFavorite.value)
    }

    @Test
    fun `toggle favorite stores current pokemon and updates state`() = runTest {
        val useCase = FakePokemonUseCase().apply {
            pokemonInfoResponse = flowOf(Resource.Success(pikachu))
        }
        val viewModel = PokemonDetailViewModel(useCase)
        viewModel.loadPokemonInfo("pikachu")
        advanceUntilIdle()
        assertFalse(viewModel.isFavorite.value)

        viewModel.toggleFavorite()
        advanceUntilIdle()

        assertEquals(25, useCase.lastUpdatedPokemon?.number)
        assertEquals("PIKACHU", useCase.lastUpdatedPokemon?.pokemonName)
        assertEquals(true, useCase.lastFavoriteValue)
        assertTrue(viewModel.isFavorite.value)
    }

    @Test
    fun `failed load exposes error message`() = runTest {
        val useCase = FakePokemonUseCase().apply {
            pokemonInfoResponse = flowOf(Resource.Error("Not found"))
        }
        val viewModel = PokemonDetailViewModel(useCase)

        viewModel.loadPokemonInfo("missingno")
        advanceUntilIdle()

        val result = viewModel.pokemonInfo.value as Resource.Error
        assertEquals("Not found", result.message)
    }
}
