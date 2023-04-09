package com.necromyd.earthquakemonitor.viewmodel


import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColor
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.necromyd.earthquakemonitor.model.Earthquake
import com.necromyd.earthquakemonitor.network.ApiService
import kotlinx.coroutines.launch
import java.lang.Exception
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.HashMap
import com.necromyd.earthquakemonitor.R

class EarthquakeViewModel : ViewModel() {

    val stateList = mutableStateListOf<Earthquake>()
    private val customList = mutableStateListOf<Earthquake>()

    private val _selectedQuake = MutableLiveData<Earthquake>()
    var selectedQuake : LiveData<Earthquake> = _selectedQuake





    init {
        viewModelScope.launch {
            getEarthquakeList()
        }
    }


    fun numberOfQuakesToday(): Int{
        val currentTime = LocalDateTime.now()
        val twentyFourHoursAgo = currentTime.minusHours(3)

        val filteredList = stateList.filter { item ->
            // Convert the date and time field to a LocalDateTime object
            val itemDateTime = LocalDateTime.parse(item.date, DateTimeFormatter.ISO_LOCAL_DATE_TIME)

            // Return true if the item's date and time field is within the last 24 hours
            itemDateTime.isAfter(twentyFourHoursAgo) && itemDateTime.isBefore(currentTime)
        }

        return filteredList.size
    }

    private fun getCustomEarthquake(filter: HashMap<String, String>){
        viewModelScope.launch {
            val apiService = ApiService.getInstance()
            try {
                val response = apiService.getCustomEarthquake(filter)
                customList.addAll(response.data)
            }catch (e: Exception){

            }
        }
    }

    fun selectedQuakeToPreview(quake : Earthquake){
        _selectedQuake.value = quake
    }

    fun getLatestEarthquake() : Earthquake{
        return stateList[0]
    }

     private fun getEarthquakeList(){
        viewModelScope.launch {
            val apiService = ApiService.getInstance()
            try {
                val response = apiService.getEarthquakes()
                Log.d("Response", response.data[0].title)
                stateList.addAll(response.data)
                _selectedQuake.value = stateList[0]
                Log.d("List has no items ? " , stateList[0].title)
            } catch (e: Exception) {
//                errorMessage = e.message.toString()
            }
        }
    }


    private fun getMostActiveCountry(): String {
        val listOfCountries = stateList.groupingBy { it.country }.eachCount()
        return listOfCountries.maxBy { it.value }.key
    }

    fun getTop5Today() : List<Earthquake>{
        var tempList = stateList.toList()
        val top5earthquakes = mutableListOf<Earthquake>()
        tempList = tempList.sortedByDescending { it.magnitude.toDouble() }

        var count = 0
        while(count < 5){
            top5earthquakes.add(tempList[count])
            count++
        }
        return top5earthquakes
    }

    fun mToast(context: Context){
        Toast.makeText(context, selectedQuake.value?.magnitude, Toast.LENGTH_LONG).show()
    }

    fun formatDate(input: String): Array<String> {
        var data = input.removeSurrounding("[", "]")
        val split = data.split("T")
        var date = split[0]
        var time = split[1]

        date = "Date : $date"
        time = "Time : $time"

        return arrayOf(date, time)
    }

    fun countryNameFormat(): String{

        return if(selectedQuake.value?.country == null || selectedQuake.value!!.country.isEmpty()){
            "N/A"
        }else{
            var countryName = selectedQuake.value?.country

            if (countryName!!.contains("(the)")){
                countryName = countryName.replace("(the)", "")
                countryName = "The $countryName"
            }
            countryName
        }

    }

    fun getMagnitudeColor(mag: Double): Color{
        val bgColor =
            when {
                mag >= 5 -> Color.Red
                mag < 5 && mag >= 3 -> Color(1.0f, 0.5f, 0f)
                else -> Color.Gray
            }
        return bgColor
    }


}

