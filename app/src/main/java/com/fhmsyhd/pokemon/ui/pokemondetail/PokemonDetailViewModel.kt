package com.fhmsyhd.pokemon.ui.pokemondetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fhmsyhd.pokemon.core.data.Resource
import com.fhmsyhd.pokemon.core.domain.model.Pokemon
import com.fhmsyhd.pokemon.core.domain.model.PokemonListEntry
import com.fhmsyhd.pokemon.core.domain.usecase.PokemonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val useCase: PokemonUseCase
) : ViewModel() {

    private val _pokemonInfo = MutableStateFlow<Resource<Pokemon>>(Resource.Loading())
    val pokemonInfo: StateFlow<Resource<Pokemon>> = _pokemonInfo.asStateFlow()

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite.asStateFlow()

    private var favoriteObserver: Job? = null
    private var pokemonLoader: Job? = null

    fun loadPokemonInfo(name: String) {
        pokemonLoader?.cancel()
        _pokemonInfo.value = Resource.Loading()
        pokemonLoader = viewModelScope.launch {
            useCase.getPokemonInfo(name).collect { result ->
                _pokemonInfo.value = result
                if (result is Resource.Success) {
                    result.data?.let { observeFavorite(it.id) }
                }
            }
        }
    }

    fun toggleFavorite() {
        val pokemon = (_pokemonInfo.value as? Resource.Success)?.data ?: return
        val favorite = PokemonListEntry(
            pokemonName = pokemon.name.uppercase(),
            imageUrl = pokemon.imageUrl,
            number = pokemon.id
        )

        viewModelScope.launch {
            useCase.setFavoritePokemon(favorite, isFavorite = !_isFavorite.value)
        }
    }

    private fun observeFavorite(number: Int) {
        favoriteObserver?.cancel()
        favoriteObserver = viewModelScope.launch {
            useCase.isFavoritePokemon(number).collect { favorite ->
                _isFavorite.value = favorite
            }
        }
    }
}
