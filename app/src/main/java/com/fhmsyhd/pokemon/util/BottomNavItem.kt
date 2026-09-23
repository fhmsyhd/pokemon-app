package com.fhmsyhd.pokemon.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object PokemonList : BottomNavItem(
        route = Routes.LIST,
        title = "Pokemon",
        icon = Icons.AutoMirrored.Filled.List
    )
    object Favorites : BottomNavItem(
        route = Routes.FAVORITES,
        title = "Favorites",
        icon = Icons.Default.Favorite
    )
}
