package com.fhmsyhd.pokemon.core.domain.model

data class Pokemon(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val types: List<Type>,
    val weight: Int,
    val height: Int,
    val stats: List<Stat>
)