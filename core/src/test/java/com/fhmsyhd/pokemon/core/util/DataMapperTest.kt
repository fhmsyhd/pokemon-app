package com.fhmsyhd.pokemon.core.util

import com.fhmsyhd.pokemon.core.data.local.entity.FavoritePokemonEntity
import com.fhmsyhd.pokemon.core.data.remote.response.ResultResponse
import com.fhmsyhd.pokemon.core.domain.model.PokemonListEntry
import org.junit.Assert.assertEquals
import org.junit.Test

class DataMapperTest {

    @Test
    fun `api result maps pokemon number and image url`() {
        val result = ResultResponse(
            name = "pikachu",
            url = "https://pokeapi.co/api/v2/pokemon/25/"
        )

        val pokemon = result.toDomain()

        assertEquals("PIKACHU", pokemon.pokemonName)
        assertEquals(25, pokemon.number)
        assertEquals(
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/25.png",
            pokemon.imageUrl
        )
    }

    @Test
    fun `favorite entity maps to domain model`() {
        val entity = FavoritePokemonEntity(
            number = 1,
            pokemonName = "BULBASAUR",
            imageUrl = "bulbasaur.png"
        )

        assertEquals(
            PokemonListEntry("BULBASAUR", "bulbasaur.png", 1),
            entity.toDomain()
        )
    }

    @Test
    fun `domain model maps to favorite entity`() {
        val pokemon = PokemonListEntry(
            pokemonName = "CHARMANDER",
            imageUrl = "charmander.png",
            number = 4
        )

        assertEquals(
            FavoritePokemonEntity(4, "CHARMANDER", "charmander.png"),
            pokemon.toFavoriteEntity()
        )
    }
}
