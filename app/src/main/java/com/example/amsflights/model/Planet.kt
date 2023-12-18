package com.example.amsflights.model

import com.google.gson.annotations.SerializedName

data class Planet(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("radius")
    val radius: Double,
    @SerializedName("distance")
    val distance: Double,
    @SerializedName("gravity")
    val gravity: Double,
    @SerializedName("image")
    val image: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("is_delete")
    val isDeleted: Boolean,
)