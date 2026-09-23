package com.fhmsyhd.pokemon.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fhmsyhd.pokemon.core.domain.model.PokemonListEntry
import com.fhmsyhd.pokemon.core.domain.usecase.PokemonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val useCase: PokemonUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(FavoritesState())
    val state: StateFlow<FavoritesState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            useCase.getFavoritePokemon().collect { favorites ->
                _state.value = FavoritesState(
                    favorites = favorites,
                    isLoading = false
                )
            }
        }
    }

    fun removeFavorite(pokemon: PokemonListEntry) {
        viewModelScope.launch {
            useCase.setFavoritePokemon(pokemon, isFavorite = false)
        }
    }
}
