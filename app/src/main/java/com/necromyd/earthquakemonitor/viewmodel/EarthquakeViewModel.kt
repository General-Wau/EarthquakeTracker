package com.necromyd.earthquakemonitor.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.necromyd.earthquakemonitor.model.Earthquake
import com.necromyd.earthquakemonitor.network.ApiService
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlin.reflect.KProperty

class EarthquakeViewModel : ViewModel() {

    var earthquakeResponse: List<Earthquake> by mutableStateOf(listOf())
    var savedEarthquake: List<Earthquake> by mutableStateOf(listOf())
    var earthquakeList: MutableList<Earthquake> = mutableListOf()

    var errorMessage: String by mutableStateOf("")

    fun getEarthquakeList() {
        viewModelScope.launch {
            val apiService = ApiService.getInstance()
            try {
                val earthquakeList = apiService.getEarthquakes()
                earthquakeResponse = earthquakeList
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    fun saveEarthquake(earthquake: Earthquake) {
        earthquakeList.add(earthquake)
        savedEarthquake = earthquakeList
    }
}

