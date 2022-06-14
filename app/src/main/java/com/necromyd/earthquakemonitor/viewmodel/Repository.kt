package com.necromyd.earthquakemonitor.viewmodel

import com.necromyd.earthquakemonitor.model.Earthquake

class Repository (){
    var earthquakeList: List<Earthquake> = emptyList()
    var savedEarthquakeList = mutableListOf<Earthquake>()
}