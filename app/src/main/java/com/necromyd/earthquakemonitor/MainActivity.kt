package com.necromyd.earthquakemonitor

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import com.necromyd.earthquakemonitor.model.Earthquake
import com.necromyd.earthquakemonitor.ui.theme.EarthquakeMonitorTheme
import com.necromyd.earthquakemonitor.view.PrimaryScreen
import com.necromyd.earthquakemonitor.viewmodel.EarthquakeViewModel

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            EarthquakeMonitorTheme {
                val earthquakeViewModel = viewModel(EarthquakeViewModel::class.java)
                PrimaryScreen(earthquakeViewModel)
//                Navigation(
//                    viewModel = earthquakeViewModel
//                )
            }
        }
    }
}

//    @Composable
//    fun ErrorMessage(viewModel: EarthquakeViewModel) {
//        Text(viewModel.errorMessage)
//    }

//    @Composable
//    fun test(stateList: List<Earthquake>) {
//        Log.d("Item in the list :", stateList.isEmpty().toString() ?: stateList[0].title)
//        LazyColumn(modifier = Modifier.fillMaxSize()) {
//            if (stateList.isEmpty()) {
//                item {
//                    CircularProgressIndicator(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .wrapContentSize(Alignment.Center)
//                    )
//                }
//            } else {
//                items(stateList) { earthquake: Earthquake ->
//                    if (earthquake.magnitude.toDouble() >= 5) {
//                        Text(text = earthquake.title)
//                        Text(text = earthquake.date)
//                        if (earthquake.country != "") {
//                            Text(text = earthquake.country)
//                        } else {
//                            Text("N/A")
//                        }
//                        Spacer(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .height(10.dp)
//                        )
//                    }
//
//                }
//            }
//        }
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    EarthquakeMonitorTheme {
//
//    }
