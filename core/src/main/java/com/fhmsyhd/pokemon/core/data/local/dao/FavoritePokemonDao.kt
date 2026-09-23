package com.fhmsyhd.pokemon.core.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fhmsyhd.pokemon.core.data.local.entity.FavoritePokemonEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritePokemonDao {
    @Query("SELECT * FROM favorite_pokemon ORDER BY number ASC")
    fun observeFavorites(): Flow<List<FavoritePokemonEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_pokemon WHERE number = :number)")
    fun observeIsFavorite(number: Int): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(pokemon: FavoritePokemonEntity)

    @Query("DELETE FROM favorite_pokemon WHERE number = :number")
    suspend fun delete(number: Int)
}
