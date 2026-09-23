package com.fhmsyhd.pokemon.ui.pokemonlist

import android.graphics.drawable.Drawable
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest
import com.fhmsyhd.pokemon.core.domain.model.PokemonListEntry
import com.fhmsyhd.pokemon.ui.common.ContentMessage

@Composable
fun PokemonListScreen(
    onNavigateToDetail: (Int, String) -> Unit,
    viewModel: PokemonListViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            PokedexHeader(
                query = state.searchQuery,
                onQueryChange = viewModel::searchPokemonList,
                onClear = { viewModel.searchPokemonList("") }
            )

            when {
                state.isLoading && state.pokemonList.isEmpty() && !state.isSearching -> {
                    PokemonGridSkeleton()
                }

                state.error.isNotBlank() && state.pokemonList.isEmpty() -> {
                    ContentMessage(
                        title = "Couldn’t load Pokémon",
                        message = "Check your internet connection and try again.",
                        actionLabel = "Try again",
                        onAction = viewModel::loadPokemonPaginated,
                        isError = true
                    )
                }

                state.pokemonList.isEmpty() && state.isSearching -> {
                    ContentMessage(
                        title = "No Pokémon found",
                        message = "We couldn’t find anything for “${state.searchQuery.trim()}”. Try a name or Pokédex number.",
                        actionLabel = "Clear search",
                        onAction = { viewModel.searchPokemonList("") }
                    )
                }

                else -> PokemonGrid(
                    state = state,
                    onLoadMore = viewModel::loadPokemonPaginated,
                    onRetry = viewModel::loadPokemonPaginated,
                    onItemClick = onNavigateToDetail,
                    calculateDominantColor = viewModel::calcDominantColor
                )
            }
        }
    }
}

@Composable
private fun PokedexHeader(
    query: String,
    onQueryChange: (String) -> Unit,
    onClear: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, top = 24.dp, end = 20.dp, bottom = 12.dp)
    ) {
        Text(
            text = "Pokédex",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = "Discover and collect your favorite Pokémon.",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 4.dp, bottom = 18.dp)
        )
        OutlinedTextField(
            value = query,
            onValueChange = onQueryChange,
            placeholder = { Text("Search by name or number") },
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = null)
            },
            trailingIcon = if (query.isNotEmpty()) {
                {
                    IconButton(onClick = onClear) {
                        Icon(Icons.Default.Close, contentDescription = "Clear search")
                    }
                }
            } else null,
            singleLine = true,
            shape = RoundedCornerShape(18.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = Color.Transparent
            ),
            modifier = Modifier
                .fillMaxWidth()
                .shadow(3.dp, RoundedCornerShape(18.dp))
        )
    }
}

@Composable
private fun PokemonGrid(
    state: PokemonListState,
    onLoadMore: () -> Unit,
    onRetry: () -> Unit,
    onItemClick: (Int, String) -> Unit,
    calculateDominantColor: (Drawable, (Color) -> Unit) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(154.dp),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        itemsIndexed(
            items = state.pokemonList,
            key = { _, pokemon -> pokemon.number }
        ) { index, pokemon ->
            if (index >= state.pokemonList.lastIndex - 3 &&
                !state.endReached &&
                !state.isLoading &&
                !state.isSearching
            ) {
                LaunchedEffect(index) { onLoadMore() }
            }
            PokemonCard(
                pokemon = pokemon,
                onItemClick = onItemClick,
                calculateDominantColor = calculateDominantColor
            )
        }

        if (state.isLoading && state.pokemonList.isNotEmpty()) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    CircularProgressIndicator(modifier = Modifier.size(28.dp))
                }
            }
        }

        if (state.error.isNotBlank() && state.pokemonList.isNotEmpty()) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                ContentMessage(
                    title = "More Pokémon couldn’t be loaded",
                    message = "Your current list is still available.",
                    actionLabel = "Retry",
                    onAction = onRetry,
                    isError = true,
                    modifier = Modifier.height(260.dp)
                )
            }
        }
    }
}

@Composable
private fun PokemonCard(
    pokemon: PokemonListEntry,
    onItemClick: (Int, String) -> Unit,
    calculateDominantColor: (Drawable, (Color) -> Unit) -> Unit
) {
    var dominantColor by remember(pokemon.number) {
        mutableStateOf(Color(0xFFB8C4D8))
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.86f)
            .shadow(4.dp, RoundedCornerShape(22.dp))
            .clip(RoundedCornerShape(22.dp))
            .background(MaterialTheme.colorScheme.surface)
            .clickable {
                onItemClick(dominantColor.toArgb(), pokemon.pokemonName)
            }
    ) {
        Box(
            modifier = Modifier
                .size(142.dp)
                .align(Alignment.TopEnd)
                .background(dominantColor.copy(alpha = 0.2f), CircleShape)
        )
        Text(
            text = "#${pokemon.number.toString().padStart(3, '0')}",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(14.dp)
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(pokemon.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = pokemon.pokemonName,
                loading = { ImagePlaceholder() },
                error = { ImagePlaceholder(showQuestionMark = true) },
                success = { success ->
                    calculateDominantColor(success.result.drawable) { dominantColor = it }
                    SubcomposeAsyncImageContent()
                },
                modifier = Modifier.size(128.dp)
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = pokemon.pokemonName.lowercase().replaceFirstChar { it.uppercase() },
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun ImagePlaceholder(showQuestionMark: Boolean = false) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant, CircleShape)
    ) {
        if (showQuestionMark) {
            Text(
                text = "?",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        } else {
            CircularProgressIndicator(
                strokeWidth = 2.dp,
                modifier = Modifier.size(28.dp)
            )
        }
    }
}

@Composable
private fun PokemonGridSkeleton() {
    val transition = rememberInfiniteTransition(label = "pokemon-skeleton")
    val alpha by transition.animateFloat(
        initialValue = 0.35f,
        targetValue = 0.75f,
        animationSpec = infiniteRepeatable(
            animation = tween(850),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pokemon-skeleton-alpha"
    )

    LazyVerticalGrid(
        columns = GridCells.Adaptive(154.dp),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(6) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .aspectRatio(0.86f)
                    .clip(RoundedCornerShape(22.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(16.dp)
            ) {
                Spacer(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = alpha))
                )
                Spacer(Modifier.height(18.dp))
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .height(18.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = alpha))
                )
            }
        }
    }
}
