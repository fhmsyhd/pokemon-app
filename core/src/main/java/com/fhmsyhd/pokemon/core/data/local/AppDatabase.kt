package com.fhmsyhd.pokemon.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fhmsyhd.pokemon.core.data.local.dao.FavoritePokemonDao
import com.fhmsyhd.pokemon.core.data.local.entity.FavoritePokemonEntity

@Database(
    entities = [FavoritePokemonEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoritePokemonDao(): FavoritePokemonDao
}
