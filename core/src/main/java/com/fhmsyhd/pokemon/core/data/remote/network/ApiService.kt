package com.fhmsyhd.pokemon.core.data.remote.network

import com.fhmsyhd.pokemon.core.data.remote.response.PokemonResponse
import com.fhmsyhd.pokemon.core.data.remote.response.PokemonListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import com.fhmsyhd.pokemon.core.util.Constant.END_POINT_POKEMON_LIST
import com.fhmsyhd.pokemon.core.util.Constant.END_POINT_POKEMON_INFO
interface ApiService {

    @GET(END_POINT_POKEMON_LIST)
    suspend fun getPokemonList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): PokemonListResponse

    @GET(END_POINT_POKEMON_INFO)
    suspend fun getPokemonInfo(
        @Path("name") name: String
    ): PokemonResponse
}