package com.example.amsflights.data

import com.example.amsflights.model.Response
import com.example.amsflights.model.Response2
import com.example.amsflights.network.ASMFlightsApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class PlanetRepositoryImpl : PlanetRepository {
    override fun getAllPlanets(search: String?): Flow<Response> =
        callbackFlow {
            trySendBlocking(
                ASMFlightsApi.api.getAllPlanets(search)
            )
            awaitClose()
        }

    override fun getPlanet(id: String): Flow<Response2> =
        callbackFlow {
            trySendBlocking(
                ASMFlightsApi.api.getPlanet(id)
            )
            awaitClose()
        }
}
