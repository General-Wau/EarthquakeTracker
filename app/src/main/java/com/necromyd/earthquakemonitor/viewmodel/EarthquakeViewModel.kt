package com.necromyd.earthquakemonitor.viewmodel

import android.util.Log
import android.util.Log.DEBUG
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.necromyd.earthquakemonitor.model.Earthquake
import com.necromyd.earthquakemonitor.network.ApiService
import com.necromyd.earthquakemonitor.network.ResponseResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.security.auth.callback.Callback
import kotlin.reflect.KProperty

class EarthquakeViewModel : ViewModel() {

    val stateList = mutableStateListOf<Earthquake>()

    init {
        viewModelScope.launch {
            getEarthquakeList()
        }
    }

     private fun getEarthquakeList(){
        viewModelScope.launch {
            val apiService = ApiService.getInstance()
            try {
                val response = apiService.getEarthquakes()
                Log.d("Response", response.data[0].title)
                stateList.addAll(response.data)
                Log.d("List has no items ? " , stateList[0].title)
            } catch (e: Exception) {
//                errorMessage = e.message.toString()
            }
        }
    }

//    fun saveEarthquake(earthquake: Earthquake) {
//        savedEarthquakeList.add(earthquake)
//    }
}

