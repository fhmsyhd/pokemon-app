package com.fhmsyhd.pokemon.ui.pokemonlist

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.fhmsyhd.pokemon.core.data.Resource
import com.fhmsyhd.pokemon.core.domain.model.PokemonListEntry
import com.fhmsyhd.pokemon.core.domain.usecase.PokemonUseCase
import com.fhmsyhd.pokemon.core.util.Constant.PAGE_SIZE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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
    private var isSearchStarting = true

    init {
        loadPokemonPaginated()
    }

    fun searchPokemonList(query: String) {
        val listToSearch = if (isSearchStarting) {
            _state.value.pokemonList
        } else {
            cachedPokemonList
        }
        viewModelScope.launch(Dispatchers.Default) {
            if (query.isEmpty()) {
                _state.update {
                    it.copy(
                        pokemonList = cachedPokemonList,
                        isSearching = false,
                        searchQuery = ""
                    )
                }
                isSearchStarting = true
                return@launch
            }

            val results = listToSearch.filter {
                it.pokemonName.contains(query.trim(), ignoreCase = true) ||
                        it.number.toString() == query.trim()
            }
            if (isSearchStarting) {
                cachedPokemonList = _state.value.pokemonList
                isSearchStarting = false
            }
            _state.update {
                it.copy(
                    pokemonList = results,
                    isSearching = true,
                    searchQuery = query
                )
            }
        }
    }

    fun loadPokemonPaginated() {
        viewModelScope.launch {
            pokemonUseCase.getPokemonList(PAGE_SIZE, curPage * PAGE_SIZE).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.update { it.copy(isLoading = true) }
                    }

                    is Resource.Success -> {
                        val data = result.data.orEmpty()
                        curPage++
                        _state.update {
                            it.copy(
                                pokemonList = it.pokemonList + data,
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
        val bmp = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)

        Palette.from(bmp).generate { palette ->
            palette?.dominantSwatch?.rgb?.let { colorValue ->
                onFinish(Color(colorValue))
            }
        }
    }
}