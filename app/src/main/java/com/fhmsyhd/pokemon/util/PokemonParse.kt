package com.fhmsyhd.pokemon.util

import androidx.compose.ui.graphics.Color
import com.fhmsyhd.pokemon.core.domain.model.Stat
import com.fhmsyhd.pokemon.core.domain.model.Type
import com.fhmsyhd.pokemon.ui.theme.AtkColor
import com.fhmsyhd.pokemon.ui.theme.DefColor
import com.fhmsyhd.pokemon.ui.theme.HPColor
import com.fhmsyhd.pokemon.ui.theme.SpAtkColor
import com.fhmsyhd.pokemon.ui.theme.SpDefColor
import com.fhmsyhd.pokemon.ui.theme.SpdColor
import com.fhmsyhd.pokemon.ui.theme.TypeBug
import com.fhmsyhd.pokemon.ui.theme.TypeDark
import com.fhmsyhd.pokemon.ui.theme.TypeDragon
import com.fhmsyhd.pokemon.ui.theme.TypeElectric
import com.fhmsyhd.pokemon.ui.theme.TypeFairy
import com.fhmsyhd.pokemon.ui.theme.TypeFighting
import com.fhmsyhd.pokemon.ui.theme.TypeFire
import com.fhmsyhd.pokemon.ui.theme.TypeFlying
import com.fhmsyhd.pokemon.ui.theme.TypeGhost
import com.fhmsyhd.pokemon.ui.theme.TypeGrass
import com.fhmsyhd.pokemon.ui.theme.TypeGround
import com.fhmsyhd.pokemon.ui.theme.TypeIce
import com.fhmsyhd.pokemon.ui.theme.TypeNormal
import com.fhmsyhd.pokemon.ui.theme.TypePoison
import com.fhmsyhd.pokemon.ui.theme.TypePsychic
import com.fhmsyhd.pokemon.ui.theme.TypeRock
import com.fhmsyhd.pokemon.ui.theme.TypeSteel
import com.fhmsyhd.pokemon.ui.theme.TypeWater

fun parseTypeToColor(type: Type): Color {
    return when(type.name.lowercase()) {
        "normal" -> TypeNormal
        "fire" -> TypeFire
        "water" -> TypeWater
        "electric" -> TypeElectric
        "grass" -> TypeGrass
        "ice" -> TypeIce
        "fighting" -> TypeFighting
        "poison" -> TypePoison
        "ground" -> TypeGround
        "flying" -> TypeFlying
        "psychic" -> TypePsychic
        "bug" -> TypeBug
        "rock" -> TypeRock
        "ghost" -> TypeGhost
        "dragon" -> TypeDragon
        "dark" -> TypeDark
        "steel" -> TypeSteel
        "fairy" -> TypeFairy
        else -> Color.Black
    }
}

fun parseStatToColor(stat: Stat): Color {
    return when(stat.name.lowercase()) {
        "hp" -> HPColor
        "attack" -> AtkColor
        "defense" -> DefColor
        "special-attack" -> SpAtkColor
        "special-defense" -> SpDefColor
        "speed" -> SpdColor
        else -> Color.White
    }
}

fun parseStatToAbbr(stat: Stat): String {
    return when(stat.name.lowercase()) {
        "hp" -> "HP"
        "attack" -> "Atk"
        "defense" -> "Def"
        "special-attack" -> "SpAtk"
        "special-defense" -> "SpDef"
        "speed" -> "Spd"
        else -> ""
    }
}