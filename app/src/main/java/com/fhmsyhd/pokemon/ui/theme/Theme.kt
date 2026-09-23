package com.fhmsyhd.pokemon.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = PokedexRed,
    onPrimary = Color.White,
    secondary = PokedexBlue,
    onSecondary = Color.White,
    tertiary = PokedexYellow,
    background = PokedexBackground,
    onBackground = PokedexInk,
    surface = Color.White,
    onSurface = PokedexInk,
    surfaceVariant = PokedexSurfaceVariant,
    onSurfaceVariant = PokedexMuted,
    error = Color(0xFFB42318)
)

@Composable
fun PokemonTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = LightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
