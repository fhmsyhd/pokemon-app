package com.fhmsyhd.pokemon.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fhmsyhd.pokemon.core.util.Constant.TABLE_FAVORITE_POKEMON

@Entity(tableName = TABLE_FAVORITE_POKEMON)
data class FavoritePokemonEntity(
    @PrimaryKey val number: Int,
    val pokemonName: String,
    val imageUrl: String
)
