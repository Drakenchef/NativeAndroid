package com.example.amsflights.model

import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("Planets")
    val planets: List<Planet>,
    @SerializedName("Flight_id")
    val flightId: Int? = null
)

data class Response2(
    @SerializedName("Planets")
    val planets: Planet,
)