package com.fhmsyhd.pokemon.ui.favorites

import com.fhmsyhd.pokemon.core.domain.model.PokemonListEntry

data class FavoritesState(
    val favorites: List<PokemonListEntry> = emptyList(),
    val isLoading: Boolean = true
)
