package com.fhmsyhd.pokemon.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object PokemonList : BottomNavItem(
        route = Routes.LIST,
        title = "Pokemon",
        icon = Icons.Default.List
    )
    object Profile : BottomNavItem(
        route = Routes.PROFILE,
        title = "Profile",
        icon = Icons.Default.Person
    )
}
