package com.fhmsyhd.pokemon.ui.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.fhmsyhd.pokemon.ui.pokemonlist.PokemonListScreen
import com.fhmsyhd.pokemon.ui.profile.ProfileScreen
import com.fhmsyhd.pokemon.util.BottomNavItem

@Composable
fun HomeScreen(
    onLogout: () -> Unit,
    onDetailNavigate: (Int, String) -> Unit
) {
    val navController = rememberNavController()

    val items = listOf(
        BottomNavItem.PokemonList,
        BottomNavItem.Profile
    )

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface
            ) {
                val currentRoute = currentRoute(navController)
                items.forEach { item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = item.title) },
                        label = { Text(item.title) },
                        selected = currentRoute == item.route,
                        onClick = {
                            if (currentRoute != item.route) {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        }
                    )
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.PokemonList.route,
            modifier = Modifier.padding(padding)
        ) {
            composable(BottomNavItem.PokemonList.route) {
                PokemonListScreen(
                    onNavigateToDetail = { dominantColor, name ->
                        onDetailNavigate(dominantColor, name)
                    }
                )            }
            composable(BottomNavItem.Profile.route) {
                ProfileScreen(onLogout = onLogout)
            }
        }
    }
}

@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}