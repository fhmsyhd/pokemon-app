package com.fhmsyhd.pokemon.core.data.repository

import com.fhmsyhd.pokemon.core.data.Resource
import com.fhmsyhd.pokemon.core.data.local.dao.FavoritePokemonDao
import com.fhmsyhd.pokemon.core.data.remote.network.ApiService
import com.fhmsyhd.pokemon.core.domain.model.Pokemon
import com.fhmsyhd.pokemon.core.domain.model.PokemonListEntry
import com.fhmsyhd.pokemon.core.domain.repository.IPokemonRepository
import com.fhmsyhd.pokemon.core.util.toDomain
import com.fhmsyhd.pokemon.core.util.toFavoriteEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokemonRepository @Inject constructor(
    private val apiService: ApiService,
    private val favoritePokemonDao: FavoritePokemonDao
) : IPokemonRepository {
    override fun getPokemonList(
        limit: Int,
        offset: Int
    ): Flow<Resource<List<PokemonListEntry>>> = flow {
        emit(Resource.Loading())
        try {
            val response = apiService.getPokemonList(limit, offset)
            val list = response.results.map { it.toDomain() }
            emit(Resource.Success(list))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }

    override fun getPokemonInfo(name: String): Flow<Resource<Pokemon>> = flow {
        emit(Resource.Loading())

        try {
            val response = apiService.getPokemonInfo(name)
            val domain = response.toDomain()
            emit(Resource.Success(domain))

        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }

    override fun getFavoritePokemon(): Flow<List<PokemonListEntry>> =
        favoritePokemonDao.observeFavorites().map { favorites ->
            favorites.map { it.toDomain() }
        }

    override fun isFavoritePokemon(number: Int): Flow<Boolean> =
        favoritePokemonDao.observeIsFavorite(number)

    override suspend fun setFavoritePokemon(
        pokemon: PokemonListEntry,
        isFavorite: Boolean
    ) {
        if (isFavorite) {
            favoritePokemonDao.upsert(pokemon.toFavoriteEntity())
        } else {
            favoritePokemonDao.delete(pokemon.number)
        }
    }
}
