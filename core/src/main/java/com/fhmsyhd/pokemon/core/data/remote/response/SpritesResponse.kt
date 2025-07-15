package com.fhmsyhd.pokemon.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class SpritesResponse(
    @SerializedName("front_default")
    val frontDefault: String,
)