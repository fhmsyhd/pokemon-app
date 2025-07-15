package com.fhmsyhd.pokemon.core.data.remote.response

data class PokemonListResponse(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<ResultResponse>
)