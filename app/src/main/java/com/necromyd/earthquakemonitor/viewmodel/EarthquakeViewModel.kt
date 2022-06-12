package com.necromyd.earthquakemonitor.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.necromyd.earthquakemonitor.model.Earthquake
import com.necromyd.earthquakemonitor.network.ApiService
import kotlinx.coroutines.launch
import java.lang.Exception

class EarthquakeViewModel : ViewModel(){

    var earthquakeResponse: List<Earthquake> by mutableStateOf(listOf())
    var errorMessage: String by mutableStateOf("")

    fun getEarthquakeList(){
        viewModelScope.launch {
            val apiService = ApiService.getInstance()
            try {
                val earthquakeList = apiService.getEarthquakes()
                earthquakeResponse = earthquakeList
            }catch (e: Exception){
                errorMessage = e.message.toString()
            }
        }
    }
}