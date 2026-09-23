package com.fhmsyhd.pokemon.ui.pokemonlist

import com.fhmsyhd.pokemon.core.data.Resource
import com.fhmsyhd.pokemon.core.domain.model.PokemonListEntry
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
class PokemonListViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val pikachu = PokemonListEntry("PIKACHU", "pikachu.png", 25)
    private val charmander = PokemonListEntry("CHARMANDER", "charmander.png", 4)

    @Test
    fun `initial load exposes pokemon and clears loading state`() = runTest {
        val useCase = FakePokemonUseCase().apply {
            pokemonListResponses += flowOf(
                Resource.Loading(),
                Resource.Success(listOf(pikachu, charmander))
            )
        }

        val viewModel = PokemonListViewModel(useCase)
        advanceUntilIdle()

        assertEquals(listOf(pikachu, charmander), viewModel.state.value.pokemonList)
        assertFalse(viewModel.state.value.isLoading)
        assertEquals("", viewModel.state.value.error)
    }

    @Test
    fun `search filters cached pokemon by name and number`() = runTest {
        val useCase = FakePokemonUseCase().apply {
            pokemonListResponses += flowOf(Resource.Success(listOf(pikachu, charmander)))
        }
        val viewModel = PokemonListViewModel(useCase)
        advanceUntilIdle()

        viewModel.searchPokemonList("pika")
        advanceUntilIdle()
        assertEquals(listOf(pikachu), viewModel.state.value.pokemonList)
        assertTrue(viewModel.state.value.isSearching)

        viewModel.searchPokemonList("4")
        advanceUntilIdle()
        assertEquals(listOf(charmander), viewModel.state.value.pokemonList)

        viewModel.searchPokemonList("")
        advanceUntilIdle()
        assertEquals(listOf(pikachu, charmander), viewModel.state.value.pokemonList)
        assertFalse(viewModel.state.value.isSearching)
    }

    @Test
    fun `retry after error loads requested page`() = runTest {
        val useCase = FakePokemonUseCase().apply {
            pokemonListResponses += flowOf(Resource.Error("Network unavailable"))
            pokemonListResponses += flowOf(Resource.Success(listOf(pikachu)))
        }
        val viewModel = PokemonListViewModel(useCase)
        advanceUntilIdle()

        assertEquals("Network unavailable", viewModel.state.value.error)
        assertFalse(viewModel.state.value.isLoading)

        viewModel.loadPokemonPaginated()
        advanceUntilIdle()

        assertEquals(listOf(pikachu), viewModel.state.value.pokemonList)
        assertEquals("", viewModel.state.value.error)
        assertEquals(2, useCase.listRequestCount)
    }

    @Test
    fun `pagination appends unique pokemon`() = runTest {
        val useCase = FakePokemonUseCase().apply {
            pokemonListResponses += flowOf(Resource.Success(listOf(pikachu)))
            pokemonListResponses += flowOf(Resource.Success(listOf(pikachu, charmander)))
        }
        val viewModel = PokemonListViewModel(useCase)
        advanceUntilIdle()

        viewModel.loadPokemonPaginated()
        advanceUntilIdle()

        assertEquals(listOf(pikachu, charmander), viewModel.state.value.pokemonList)
    }
}
