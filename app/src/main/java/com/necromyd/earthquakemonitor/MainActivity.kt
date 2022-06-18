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
import com.necromyd.earthquakemonitor.model.Earthquake
import com.necromyd.earthquakemonitor.ui.theme.EarthquakeMonitorTheme
import com.necromyd.earthquakemonitor.viewmodel.EarthquakeViewModel

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var text by remember {
                mutableStateOf("")
            }
            EarthquakeMonitorTheme {
                // A surface container using the 'background' color from the theme
                val earthquakeViewModel = viewModel(EarthquakeViewModel::class.java)
                test(earthquakeViewModel.stateList)
//                    Navigation(
//                        viewModel = earthquakeViewModel
//                    )


            }
        }
    }

//    @Composable
//    fun ErrorMessage(viewModel: EarthquakeViewModel) {
//        Text(viewModel.errorMessage)
//    }

    @Composable
    fun test(stateList: List<Earthquake>) {
        Log.d("Item in the list :", stateList.isEmpty().toString()?: stateList[0].title)
        LazyColumn (modifier = Modifier.fillMaxSize()){
            if (stateList.isEmpty()) {
                item {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentSize(Alignment.Center)
                    )
                }
            } else {
                items(stateList) { earthquake: Earthquake ->
                    Text(text = earthquake.title)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    EarthquakeMonitorTheme {

    }
}