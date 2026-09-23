package com.fhmsyhd.pokemon.ui.favorites

import com.fhmsyhd.pokemon.core.domain.model.PokemonListEntry
import com.fhmsyhd.pokemon.testutil.FakePokemonUseCase
import com.fhmsyhd.pokemon.testutil.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FavoritesViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `favorites are observed and can be removed`() = runTest {
        val pikachu = PokemonListEntry("PIKACHU", "pikachu.png", 25)
        val useCase = FakePokemonUseCase().apply {
            favorites.value = listOf(pikachu)
        }
        val viewModel = FavoritesViewModel(useCase)
        advanceUntilIdle()

        assertEquals(listOf(pikachu), viewModel.state.value.favorites)
        assertFalse(viewModel.state.value.isLoading)

        viewModel.removeFavorite(pikachu)
        advanceUntilIdle()

        assertTrue(viewModel.state.value.favorites.isEmpty())
        assertEquals(false, useCase.lastFavoriteValue)
        assertEquals(pikachu, useCase.lastUpdatedPokemon)
    }
}
