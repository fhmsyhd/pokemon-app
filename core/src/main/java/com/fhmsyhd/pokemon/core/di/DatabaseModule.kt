package com.fhmsyhd.pokemon.core.di

import android.content.Context
import androidx.room.Room
import com.fhmsyhd.pokemon.core.data.local.AppDatabase
import com.fhmsyhd.pokemon.core.data.local.dao.FavoritePokemonDao
import com.fhmsyhd.pokemon.core.util.Constant.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            DATABASE_NAME
        ).fallbackToDestructiveMigration(dropAllTables = true)
            .build()
    }

    @Provides
    fun provideFavoritePokemonDao(database: AppDatabase): FavoritePokemonDao {
        return database.favoritePokemonDao()
    }
}
