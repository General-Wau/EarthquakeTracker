package com.necromyd.earthquakemonitor.view

import android.text.TextUtils.isEmpty
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.necromyd.earthquakemonitor.WindowSize
import com.necromyd.earthquakemonitor.WindowType
import com.necromyd.earthquakemonitor.model.Earthquake
import com.necromyd.earthquakemonitor.rememberWindowSize
import com.necromyd.earthquakemonitor.ui.theme.EarthquakeMonitorTheme
import com.necromyd.earthquakemonitor.viewmodel.EarthquakeViewModel

@Composable
fun EarthquakeDisplay(windowSize: WindowSize, viewModel: EarthquakeViewModel) {

    if (viewModel.stateList.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            CircularProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.Center)
            )
        }
    } else {
        val earthquake = viewModel.stateList[0]
        Surface() {
            if (windowSize.width == WindowType.Expanded) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(text = earthquake.title)
                        Text(text = earthquake.country)
                        Text(text = earthquake.place)
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(text = earthquake.date)
                        Text(text = earthquake.magnitude)
                        Text(text = earthquake.tsunami)
                        Text(text = earthquake.depth)
                    }
                }

                Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                    Button(onClick = { /*send earthquake.url to browser*/ }) {
                        Text(text = "View online")
                    }
                    Button(onClick = { /*send latitude and longitude to google map activity*/ }) {
                        Text(text = "Check Location")
                    }
                    Button(onClick = { /*add to list of saved earthquakes*/ }) {
                        Text(text = "Save to List")
                    }
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    Text(text = earthquake.title)
                    Text(text = earthquake.date)
                    Text(text = earthquake.magnitude)
                    Text(text = earthquake.tsunami)
                    Text(text = earthquake.depth)
                    Text(text = earthquake.country)
                    Text(text = earthquake.place)

                    Row(horizontalArrangement = Arrangement.SpaceAround) {
                        Button(onClick = { /*send earthquake.url to browser*/ }) {
                            Text(text = "View online")
                        }
                        Button(onClick = { /*send latitude and longitude to google map activity*/ }) {
                            Text(text = "Check Location")
                        }
                        Button(onClick = { /*add to list of saved earthquakes*/ }) {
                            Text(text = "Save to List")
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreenBottomSheet(
    navController: NavController,
    windowSize: WindowSize,
    viewModel: EarthquakeViewModel
) {
    val sheetState = rememberBottomSheetState(
        initialValue = BottomSheetValue.Collapsed,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy
        )
    )
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentAlignment = Alignment.Center
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp)
                        .clickable { }
                ) {
                    if (viewModel.stateList.isEmpty()) {
                        item {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentSize(Alignment.Center)
                            )
                        }
                    } else {
                        items(viewModel.stateList) { earthquake ->
                            Text(text = earthquake.title)
                            Text(text = earthquake.date)
                            Text(text = earthquake.place)
                        }
                    }
                }
            }
        },
        sheetBackgroundColor = Color.Green,
        sheetPeekHeight = 10.dp // sets how much closed sheet is visible
    ) {
//        EarthquakeDisplay(
//            windowSize = windowSize,
//            earthquake = viewModel.earthquakeList[0]
//        )
    }
}


@Preview
@Composable
fun previewMainScreen() {
    EarthquakeMonitorTheme {
        val windowSize = rememberWindowSize()
        val viewModel = EarthquakeViewModel()
        val navController = rememberNavController()
        Surface(modifier = Modifier.fillMaxSize()) {
//            MainScreenBottomSheet(
//                navController,
//                windowSize = windowSize,
//                viewModel
//            )
        }
    }
}

/** PLAN
 * Main screen should have Navigation drawer at the top with buttons for "this screen" , "custom get request" , "map screen" , "app options"
 * At the bottom there should be a BottomSheet that contains 10 more latest earthquakes, clicking on one will display it as the main feature in the middle of the screen
 * Middle of the screen will auto display the details of the last earthquake, it will also contain the button that will launch google map composable screen , add to favorite list button too
 * Custom get request will give user the options to query more specific data about earthquakes with provided options like time frame , country , magnitude , number of results etc
 * Map screen will show where the latest or the clicked earthquake happened
 * App options will have some minor options like notification when a new earthquake happens , etc
 */