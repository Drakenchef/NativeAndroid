package com.example.amsflights.data

import com.example.amsflights.model.Response
import com.example.amsflights.model.Response2
import kotlinx.coroutines.flow.Flow

interface PlanetRepository {
    fun getAllPlanets(search: String? = null): Flow<Response>
    fun getPlanet(id: String): Flow<Response2>
}