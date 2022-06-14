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

    private val repository: Repository by mutableStateOf(Repository())
    private var errorMessage: String by mutableStateOf("")

    fun getEarthquakeList() {
        viewModelScope.launch {
            val apiService = ApiService.getInstance()
            try {
                val earthquakeList = apiService.getEarthquakes()
                repository.earthquakeList = earthquakeList
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    fun saveEarthquake(earthquake: Earthquake) {
        repository.savedEarthquakeList.add(earthquake)
    }
}

