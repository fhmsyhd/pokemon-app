package com.fhmsyhd.pokemon.ui.pokemonuser

data class AuthState(
    val username: String = "",
    val password: String = "",
    val errorMessage: String = "",
    val isLoading: Boolean = false
)