package com.necromyd.earthquakemonitor.network

import com.necromyd.earthquakemonitor.BuildConfig
import com.necromyd.earthquakemonitor.model.Earthquake
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers

interface ApiService {
    @Headers(
        BuildConfig.key,
        BuildConfig.address
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