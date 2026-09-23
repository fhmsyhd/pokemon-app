package com.fhmsyhd.pokemon.ui.pokemonlist

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.compose.ui.graphics.Color
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.fhmsyhd.pokemon.core.data.Resource
import com.fhmsyhd.pokemon.core.domain.model.PokemonListEntry
import com.fhmsyhd.pokemon.core.domain.usecase.PokemonUseCase
import com.fhmsyhd.pokemon.core.util.Constant.PAGE_SIZE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val pokemonUseCase: PokemonUseCase
) : ViewModel() {

    private var curPage = 0

    private val _state = MutableStateFlow(PokemonListState())
    val state = _state.asStateFlow()

    private var cachedPokemonList: List<PokemonListEntry> = emptyList()
    private var searchJob: Job? = null

    init {
        loadPokemonPaginated()
    }

    fun searchPokemonList(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            val normalizedQuery = query.trim()
            val results = filterPokemon(normalizedQuery)
            _state.update {
                it.copy(
                    pokemonList = results,
                    isSearching = normalizedQuery.isNotEmpty(),
                    searchQuery = query
                )
            }
        }
    }

    fun loadPokemonPaginated() {
        if (_state.value.isLoading || _state.value.endReached) return
        _state.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            pokemonUseCase.getPokemonList(PAGE_SIZE, curPage * PAGE_SIZE).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.update { it.copy(isLoading = true) }
                    }

                    is Resource.Success -> {
                        val data = result.data.orEmpty()
                        cachedPokemonList = (cachedPokemonList + data).distinctBy { it.number }
                        curPage++
                        _state.update {
                            it.copy(
                                pokemonList = filterPokemon(it.searchQuery.trim()),
                                isLoading = false,
                                endReached = data.isEmpty(),
                                error = ""
                            )
                        }
                    }

                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = result.message ?: "Unknown error"
                            )
                        }
                    }
                }
            }
        }
    }

    fun calcDominantColor(drawable: Drawable, onFinish: (Color) -> Unit) {
        val bmp = drawable.toBitmap().copy(Bitmap.Config.ARGB_8888, true)

        Palette.from(bmp).generate { palette ->
            palette?.dominantSwatch?.rgb?.let { colorValue ->
                onFinish(Color(colorValue))
            }
        }
    }

    private fun filterPokemon(query: String): List<PokemonListEntry> {
        if (query.isEmpty()) return cachedPokemonList

        return cachedPokemonList.filter {
            it.pokemonName.contains(query, ignoreCase = true) ||
                it.number.toString() == query
        }
    }
}
