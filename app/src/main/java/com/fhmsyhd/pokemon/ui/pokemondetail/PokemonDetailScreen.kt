package com.fhmsyhd.pokemon.ui.pokemondetail

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.fhmsyhd.pokemon.R
import com.fhmsyhd.pokemon.core.data.Resource
import com.fhmsyhd.pokemon.core.domain.model.Pokemon
import com.fhmsyhd.pokemon.core.domain.model.Stat
import com.fhmsyhd.pokemon.core.domain.model.Type
import com.fhmsyhd.pokemon.ui.common.ContentMessage
import com.fhmsyhd.pokemon.ui.common.LottieLoader
import com.fhmsyhd.pokemon.util.parseStatToAbbr
import com.fhmsyhd.pokemon.util.parseStatToColor
import com.fhmsyhd.pokemon.util.parseTypeToColor

@Composable
fun PokemonDetailScreen(
    dominantColor: Color,
    pokemonName: String,
    navController: NavController,
    viewModel: PokemonDetailViewModel = hiltViewModel()
) {
    LaunchedEffect(pokemonName) {
        viewModel.loadPokemonInfo(pokemonName)
    }

    val pokemonInfo by viewModel.pokemonInfo.collectAsState()
    val isFavorite by viewModel.isFavorite.collectAsState()

    when (val result = pokemonInfo) {
        is Resource.Loading -> PokemonDetailLoading(
            dominantColor = dominantColor,
            onBack = navController::popBackStack
        )

        is Resource.Error -> PokemonDetailError(
            message = result.message,
            onBack = navController::popBackStack,
            onRetry = { viewModel.loadPokemonInfo(pokemonName) }
        )

        is Resource.Success -> result.data?.let { pokemon ->
            PokemonDetailContent(
                pokemon = pokemon,
                dominantColor = dominantColor,
                isFavorite = isFavorite,
                onBack = navController::popBackStack,
                onFavoriteClick = viewModel::toggleFavorite
            )
        } ?: PokemonDetailError(
            message = null,
            onBack = navController::popBackStack,
            onRetry = { viewModel.loadPokemonInfo(pokemonName) }
        )
    }
}

@Composable
private fun PokemonDetailContent(
    pokemon: Pokemon,
    dominantColor: Color,
    isFavorite: Boolean,
    onBack: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    val heroColor = dominantColor.takeUnless { it == Color.White } ?: MaterialTheme.colorScheme.secondary
    val onHero = if (heroColor.luminance() > 0.55f) Color(0xFF172033) else Color.White

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        heroColor,
                        heroColor.copy(alpha = 0.82f),
                        MaterialTheme.colorScheme.background
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(330.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(240.dp)
                        .align(Alignment.Center)
                        .background(Color.White.copy(alpha = 0.14f), CircleShape)
                )
                DetailActionBar(
                    isFavorite = isFavorite,
                    onBack = onBack,
                    onFavoriteClick = onFavoriteClick,
                    iconColor = onHero,
                    modifier = Modifier.align(Alignment.TopCenter)
                )
                AsyncImage(
                    model = pokemon.imageUrl,
                    contentDescription = pokemon.name,
                    modifier = Modifier
                        .size(245.dp)
                        .align(Alignment.BottomCenter)
                )
            }

            Surface(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(start = 20.dp, top = 26.dp, end = 20.dp, bottom = 36.dp)
                ) {
                    Text(
                        text = "#${pokemon.id.toString().padStart(3, '0')}",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = pokemon.name.replaceFirstChar { it.uppercase() },
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                    PokemonTypes(types = pokemon.types)

                    SectionTitle("About")
                    PokemonMeasurements(
                        weight = pokemon.weight / 10f,
                        height = pokemon.height / 10f
                    )

                    SectionTitle("Base stats")
                    PokemonStats(stats = pokemon.stats)
                }
            }
        }
    }
}

@Composable
private fun DetailActionBar(
    isFavorite: Boolean,
    onBack: () -> Unit,
    onFavoriteClick: () -> Unit,
    iconColor: Color,
    showFavorite: Boolean = true,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 10.dp)
    ) {
        DetailActionButton(onClick = onBack) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = iconColor
            )
        }
        if (showFavorite) {
            DetailActionButton(onClick = onFavoriteClick) {
                Icon(
                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites",
                    tint = iconColor
                )
            }
        }
    }
}

@Composable
private fun DetailActionButton(
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Surface(
        color = Color.Black.copy(alpha = 0.12f),
        shape = CircleShape
    ) {
        IconButton(onClick = onClick, content = content)
    }
}

@Composable
private fun PokemonTypes(types: List<Type>) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.padding(top = 14.dp, bottom = 8.dp)
    ) {
        types.forEach { type ->
            Surface(
                color = parseTypeToColor(type),
                shape = CircleShape
            ) {
                Text(
                    text = type.name.uppercase(),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 18.dp, vertical = 8.dp)
                )
            }
        }
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp, bottom = 12.dp)
    )
}

@Composable
private fun PokemonMeasurements(weight: Float, height: Float) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        MeasurementCard(
            label = "Weight",
            value = "$weight kg",
            iconRes = R.drawable.ic_weight,
            modifier = Modifier.weight(1f)
        )
        MeasurementCard(
            label = "Height",
            value = "$height m",
            iconRes = R.drawable.ic_height,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun MeasurementCard(
    label: String,
    value: String,
    iconRes: Int,
    modifier: Modifier = Modifier
) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant,
        shape = RoundedCornerShape(18.dp),
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                painter = painterResource(iconRes),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.size(34.dp)
            )
            Spacer(Modifier.width(12.dp))
            Column {
                Text(
                    text = value,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun PokemonStats(stats: List<Stat>) {
    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        stats.forEachIndexed { index, stat ->
            PokemonStatRow(stat = stat, animationDelay = index * 80)
        }
    }
}

@Composable
private fun PokemonStatRow(stat: Stat, animationDelay: Int) {
    val progress by animateFloatAsState(
        targetValue = (stat.value / 255f).coerceIn(0f, 1f),
        animationSpec = tween(durationMillis = 700, delayMillis = animationDelay),
        label = "${stat.name}-progress"
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = parseStatToAbbr(stat),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.width(54.dp)
        )
        Text(
            text = stat.value.toString(),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.End,
            modifier = Modifier.width(34.dp)
        )
        Spacer(Modifier.width(12.dp))
        LinearProgressIndicator(
            progress = { progress },
            color = parseStatToColor(stat),
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
            modifier = Modifier
                .weight(1f)
                .height(9.dp)
                .clip(CircleShape)
        )
    }
}

@Composable
private fun PokemonDetailLoading(
    dominantColor: Color,
    onBack: () -> Unit
) {
    val transition = rememberInfiniteTransition(label = "detail-skeleton")
    val alpha by transition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.7f,
        animationSpec = infiniteRepeatable(tween(850), RepeatMode.Reverse),
        label = "detail-skeleton-alpha"
    )
    val heroColor = dominantColor.takeUnless { it == Color.White } ?: MaterialTheme.colorScheme.secondary

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(heroColor)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(310.dp)
        ) {
            DetailActionBar(
                isFavorite = false,
                onBack = onBack,
                onFavoriteClick = {},
                iconColor = Color.White,
                showFavorite = false
            )
            LottieLoader(
                size = 150.dp,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        Surface(
            color = MaterialTheme.colorScheme.surface,
            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(24.dp)
            ) {
                SkeletonLine(90, alpha)
                Spacer(Modifier.height(14.dp))
                SkeletonLine(180, alpha)
                Spacer(Modifier.height(30.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    SkeletonBlock(alpha, Modifier.weight(1f))
                    SkeletonBlock(alpha, Modifier.weight(1f))
                }
                Spacer(Modifier.height(30.dp))
                repeat(4) {
                    SkeletonLine(300, alpha)
                    Spacer(Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
private fun SkeletonLine(width: Int, alpha: Float) {
    Spacer(
        modifier = Modifier
            .width(width.dp)
            .height(18.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = alpha))
    )
}

@Composable
private fun SkeletonBlock(alpha: Float, modifier: Modifier = Modifier) {
    Spacer(
        modifier = modifier
            .height(84.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = alpha))
    )
}

@Composable
private fun PokemonDetailError(
    message: String?,
    onBack: () -> Unit,
    onRetry: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        DetailActionBar(
            isFavorite = false,
            onBack = onBack,
            onFavoriteClick = {},
            iconColor = MaterialTheme.colorScheme.onBackground,
            showFavorite = false,
            modifier = Modifier.align(Alignment.TopCenter)
        )
        ContentMessage(
            title = "Pokémon unavailable",
            message = message?.takeIf { it.isNotBlank() }
                ?: "We couldn’t load this Pokémon. Please try again.",
            actionLabel = "Try again",
            onAction = onRetry,
            isError = true,
            modifier = Modifier.padding(top = 56.dp)
        )
    }
}
