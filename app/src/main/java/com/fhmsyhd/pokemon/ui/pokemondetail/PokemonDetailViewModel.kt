package com.fhmsyhd.pokemon.ui.pokemondetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fhmsyhd.pokemon.core.data.Resource
import com.fhmsyhd.pokemon.core.domain.model.Pokemon
import com.fhmsyhd.pokemon.core.domain.usecase.PokemonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val useCase: PokemonUseCase
) : ViewModel() {

    private val _pokemonInfo = MutableStateFlow<Resource<Pokemon>>(Resource.Loading())
    val pokemonInfo: StateFlow<Resource<Pokemon>> = _pokemonInfo

    fun loadPokemonInfo(name: String) {
        viewModelScope.launch {
            useCase.getPokemonInfo(name).collect { result ->
                _pokemonInfo.value = result
            }
        }
    }
}
