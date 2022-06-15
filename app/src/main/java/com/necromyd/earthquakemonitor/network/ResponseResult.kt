package com.necromyd.earthquakemonitor.network

import com.necromyd.earthquakemonitor.model.Earthquake

data class ResponseResult(val data: List<Earthquake>) {
}