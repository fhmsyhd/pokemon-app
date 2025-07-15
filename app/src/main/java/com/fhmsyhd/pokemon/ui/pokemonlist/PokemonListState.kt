package com.fhmsyhd.pokemon.ui.pokemonlist

import com.fhmsyhd.pokemon.core.domain.model.PokemonListEntry

data class PokemonListState(
    val pokemonList: List<PokemonListEntry> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = "",
    val endReached: Boolean = false,
    val isSearching: Boolean = false,
    val searchQuery: String = ""
)