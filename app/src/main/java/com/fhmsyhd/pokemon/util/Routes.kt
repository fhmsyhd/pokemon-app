package com.fhmsyhd.pokemon.util

object Routes {
    const val SPLASH = "splash_screen"
    const val HOME = "home_screen"
    const val LIST = "pokemon_list_screen"
    const val DETAIL = "pokemon_detail_screen/{dominantColor}/{pokemonName}"

    const val REGISTER = "register_screen"

    const val LOGIN = "login_screen"
    const val PROFILE = "profile_screen"

    fun detailRoute(dominantColor: Int, pokemonName: String): String {
        return "pokemon_detail_screen/$dominantColor/$pokemonName"
    }
}