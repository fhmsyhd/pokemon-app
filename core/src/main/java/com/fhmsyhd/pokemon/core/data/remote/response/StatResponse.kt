package com.fhmsyhd.pokemon.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class StatResponse(
    @SerializedName("base_stat")
    val baseStat: Int,
    val effort: Int,
    val stat: StatXResponse
)