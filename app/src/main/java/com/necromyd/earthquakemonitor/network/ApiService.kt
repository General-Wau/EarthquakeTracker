package com.necromyd.earthquakemonitor.network

import com.necromyd.earthquakemonitor.model.Earthquake
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers

interface ApiService {
    @Headers(
        "X-RapidAPI-Key: ff777014e6mshb47244daaee6ba8p1a17ebjsn5b30eda1f9c8",
        "X-RapidAPI-Host: everyearthquake.p.rapidapi.com"
    )
    @GET("earthquakes")
    suspend fun getEarthquakes(): List<Earthquake>

    companion object {
        var apiService: ApiService? = null
        fun getInstance(): ApiService {
            if (apiService == null) {
                apiService = Retrofit.Builder()
                    .baseUrl("https://everyearthquake.p.rapidapi.com/earthquakes?start=1&count=10&type=earthquake")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(ApiService::class.java)
            }
            return apiService!!
        }
    }
}