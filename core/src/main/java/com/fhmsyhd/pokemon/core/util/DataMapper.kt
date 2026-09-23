package com.fhmsyhd.pokemon.core.util

import com.fhmsyhd.pokemon.core.data.local.entity.FavoritePokemonEntity
import com.fhmsyhd.pokemon.core.data.remote.response.PokemonResponse
import com.fhmsyhd.pokemon.core.data.remote.response.ResultResponse
import com.fhmsyhd.pokemon.core.data.remote.response.StatResponse
import com.fhmsyhd.pokemon.core.data.remote.response.TypeResponse
import com.fhmsyhd.pokemon.core.domain.model.Pokemon
import com.fhmsyhd.pokemon.core.domain.model.PokemonListEntry
import com.fhmsyhd.pokemon.core.domain.model.Stat
import com.fhmsyhd.pokemon.core.domain.model.Type
import com.fhmsyhd.pokemon.core.util.Constant.BASE_IMAGE_URL

fun PokemonResponse.toDomain(): Pokemon {
    return Pokemon(
        id = id,
        name = name,
        imageUrl = sprites.frontDefault,
        types = types.map { it.toDomain() },
        weight = weight,
        height = height,
        stats = stats.map { it.toDomain() }
    )
}

private fun TypeResponse.toDomain(): Type {
    return Type(
        name = type.name
    )
}

private fun StatResponse.toDomain(): Stat {
    return Stat(
        name = stat.name,
        value = baseStat
    )
}

fun ResultResponse.toDomain(): PokemonListEntry {
    val number = if (url.endsWith("/")) {
        url.dropLast(1).takeLastWhile { it.isDigit() }
    } else {
        url.takeLastWhile { it.isDigit() }
    }
    val imageUrl = "${BASE_IMAGE_URL}$number.png"

    return PokemonListEntry(
        pokemonName = name.uppercase(),
        imageUrl = imageUrl,
        number = number.toInt()
    )
}

fun FavoritePokemonEntity.toDomain(): PokemonListEntry {
    return PokemonListEntry(
        pokemonName = pokemonName,
        imageUrl = imageUrl,
        number = number
    )
}

fun PokemonListEntry.toFavoriteEntity(): FavoritePokemonEntity {
    return FavoritePokemonEntity(
        number = number,
        pokemonName = pokemonName,
        imageUrl = imageUrl
    )
}
