package com.necromyd.earthquakemonitor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.necromyd.earthquakemonitor.ui.theme.EarthquakeMonitorTheme
import com.necromyd.earthquakemonitor.viewmodel.EarthquakeViewModel

class MainActivity : ComponentActivity() {

    private val earthquakeViewModel by viewModels<EarthquakeViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EarthquakeMonitorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    earthquakeViewModel.getEarthquakeList()
                    test()
                    ErrorMessage()
//                    Navigation(
//                        viewModel = earthquakeViewModel
//                    )
                }

            }
        }
    }

    @Composable
    fun ErrorMessage() {
        Text(earthquakeViewModel.errorMessage)
    }

    @Composable
    fun test() {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(earthquakeViewModel.repository.earthquakeList) { earthquake ->
                Text(text = earthquake.title)
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