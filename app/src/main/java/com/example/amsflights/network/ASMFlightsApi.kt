package com.example.amsflights.network

import com.example.amsflights.model.Response
import com.example.amsflights.model.Response2
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ASMFlightsApi {

    @GET("/Planets")
    suspend fun getAllPlanets(@Query("search") search: String? = null): Response

    @GET("/Planet/{id}")
    suspend fun getPlanet(@Path("id") id: String): Response2

    companion object RetrofitBuilder {
        private const val BASE_URL = "http://192.168.44.122:8888/"

        private fun getRetrofit(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val api: ASMFlightsApi = getRetrofit().create()
    }

}
