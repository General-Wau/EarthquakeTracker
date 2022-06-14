package com.necromyd.earthquakemonitor.model

data class Earthquake(
    val id: String,
    val magnitude: String,
    val title: String,
    val date: String,
    val updated: String,
    val url: String,
    val tsunami: String,
    val depth: String,
    val latitude: String,
    val longitude: String,
    val place: String,
    val country: String
)
