package com.fhmsyhd.pokemon.ui.pokemonsplash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.fhmsyhd.pokemon.R
import com.fhmsyhd.pokemon.ui.common.LottieLoader
import com.fhmsyhd.pokemon.ui.theme.LightBlue
import com.fhmsyhd.pokemon.util.Routes
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: SplashViewModel = hiltViewModel()
) {
    val isLoggedIn by viewModel.getIsLoggedIn().collectAsState(initial = false)

    LaunchedEffect(Unit) {
        delay(3000L)

        if (isLoggedIn) {
            navController.navigate(Routes.HOME) {
                popUpTo(0) { inclusive = true }
            }
        } else {
            navController.navigate(Routes.LOGIN) {
                popUpTo(0) { inclusive = true }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBlue),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_international_pok_mon_logo),
                contentDescription = "Pokemon Logo"
            )
            LottieLoader(assetName = "Pikachu.json", size = 200.dp)
        }
    }
}
