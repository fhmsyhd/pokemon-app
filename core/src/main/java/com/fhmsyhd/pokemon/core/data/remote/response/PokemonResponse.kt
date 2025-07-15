package com.fhmsyhd.pokemon.core.data.remote.response

data class PokemonResponse(
    val height: Int,
    val id: Int,
    val name: String,
    val sprites: SpritesResponse,
    val stats: List<StatResponse>,
    val types: List<TypeResponse>,
    val weight: Int
)