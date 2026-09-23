package com.fhmsyhd.pokemon.util

object Routes {
    const val HOME = "home_screen"
    const val LIST = "pokemon_list_screen"
    const val DETAIL = "pokemon_detail_screen/{dominantColor}/{pokemonName}"
    const val FAVORITES = "favorites_screen"

    fun detailRoute(dominantColor: Int, pokemonName: String): String {
        return "pokemon_detail_screen/$dominantColor/$pokemonName"
    }
}
