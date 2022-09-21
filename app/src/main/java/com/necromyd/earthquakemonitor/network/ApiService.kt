package com.necromyd.earthquakemonitor.network

import com.necromyd.earthquakemonitor.BuildConfig
import com.necromyd.earthquakemonitor.model.Earthquake
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface ApiService {
    @Headers(
        BuildConfig.key,
        BuildConfig.address
    )
    @GET("earthquakes")
    suspend fun getEarthquakes(): ResponseResult

    @Headers(
        BuildConfig.key,
        BuildConfig.address
    )
    @GET("earthquakes")
    suspend fun getCustomEarthquake(@QueryMap filter: HashMap<String,String>): ResponseResult

    companion object {
        var apiService: ApiService? = null
        fun getInstance(): ApiService {
            if (apiService == null) {
                apiService = Retrofit.Builder()
                    .baseUrl("https://everyearthquake.p.rapidapi.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(ApiService::class.java)
            }
            return apiService!!
        }
    }
}