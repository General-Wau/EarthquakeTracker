package com.necromyd.earthquakemonitor.viewmodel

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.necromyd.earthquakemonitor.model.Earthquake
import com.necromyd.earthquakemonitor.network.ApiService
import com.necromyd.earthquakemonitor.network.ResponseResult
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.security.auth.callback.Callback
import kotlin.reflect.KProperty

class EarthquakeViewModel : ViewModel() {

    val repository: Repository by mutableStateOf(Repository())
    var errorMessage: String by mutableStateOf("")

//    init {
//        viewModelScope.launch {
//            getEarthquakeList()
//        }
//    }

     fun getEarthquakeList() {
        viewModelScope.launch {
            val apiService = ApiService.getInstance()
            try {
                val response = apiService.getEarthquakes()
                repository.earthquakeList = response.data
                
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    fun saveEarthquake(earthquake: Earthquake) {
        repository.savedEarthquakeList.add(earthquake)
    }
}

