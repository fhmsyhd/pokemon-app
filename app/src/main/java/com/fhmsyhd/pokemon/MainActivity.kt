package com.fhmsyhd.pokemon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.fhmsyhd.pokemon.ui.home.HomeScreen
import com.fhmsyhd.pokemon.ui.pokemondetail.PokemonDetailScreen
import com.fhmsyhd.pokemon.ui.pokemonsplash.SplashScreen
import com.fhmsyhd.pokemon.ui.pokemonuser.LoginScreen
import com.fhmsyhd.pokemon.ui.pokemonuser.RegisterScreen
import com.fhmsyhd.pokemon.ui.theme.PokemonTheme
import com.fhmsyhd.pokemon.util.Routes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokemonTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Routes.SPLASH
                ) {
                    composable(Routes.SPLASH) {
                        SplashScreen(navController = navController)
                    }
                    composable(Routes.LOGIN) {
                        LoginScreen(navController = navController)
                    }
                    composable(Routes.REGISTER) {
                        RegisterScreen(navController = navController)
                    }
                    composable(Routes.HOME) {
                        HomeScreen(
                            onLogout = {
                                navController.navigate(Routes.LOGIN) {
                                    popUpTo(0) { inclusive = true }
                                }
                            },
                            onDetailNavigate = { dominantColor, name ->
                                navController.navigate(Routes.detailRoute(dominantColor, name))
                            }
                        )
                    }
                    composable(
                        route = Routes.DETAIL,
                        arguments = listOf(
                            navArgument("dominantColor") {
                                type = NavType.IntType
                            },
                            navArgument("pokemonName") {
                                type = NavType.StringType
                            }
                        )
                    ) { backStackEntry ->
                        val dominantColor = remember {
                            val color = backStackEntry.arguments?.getInt("dominantColor")
                            color?.let { Color(it) } ?: Color.White
                        }
                        val pokemonName = remember {
                            backStackEntry.arguments?.getString("pokemonName")
                        }

                        PokemonDetailScreen(
                            dominantColor = dominantColor,
                            pokemonName = pokemonName?.lowercase() ?: "",
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}