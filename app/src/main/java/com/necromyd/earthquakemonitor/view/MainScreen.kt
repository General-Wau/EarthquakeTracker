package com.necromyd.earthquakemonitor.view

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import com.necromyd.earthquakemonitor.Screen
import com.necromyd.earthquakemonitor.WindowSize
import com.necromyd.earthquakemonitor.WindowType
import com.necromyd.earthquakemonitor.viewmodel.EarthquakeViewModel
import kotlinx.coroutines.launch
import kotlin.concurrent.thread


@Composable
fun EarthquakeDisplay(
    navController: NavController,
    windowSize: WindowSize,
    viewModel: EarthquakeViewModel
) {
    if (viewModel.stateList.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.Center)
            )
        }
    } else {
        EarthquakeBottomSheetScaffold(navController, windowSize, viewModel)
    }

}

@Composable
fun TopAppBar() {
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(150.dp)
        .background(MaterialTheme.colors.primary),
    ) {

    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EarthquakeBottomSheetScaffold(
    navController: NavController,
    windowSize: WindowSize,
    viewModel: EarthquakeViewModel
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screen = screenHeight - (screenHeight / 4)

    val sheetState = rememberBottomSheetState(
        initialValue = BottomSheetValue.Collapsed,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy
        )
    )
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )
    val scope = rememberCoroutineScope()
    var quakeState = remember {mutableStateOf(viewModel.stateList[0])}

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(screen)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp)
                        .clickable { }
                ) {

                    items(viewModel.stateList) { quake ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp)
                        ) {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp)
                                    .clickable {
                                        scope.launch {
                                            quakeState.value = quake
                                            if (sheetState.isExpanded) sheetState.collapse()
                                        }
                                    },
                                backgroundColor = Color.White
                            ) {
                                Column() {
                                    Text(text = quake.title)
                                    Text(text = quake.date)
                                    Text(text = quake.place)
                                }
                            }
                        }
                    }
                }
            }
        },
        sheetBackgroundColor = Color.Cyan,
        sheetPeekHeight = 30.dp // sets how much closed sheet is visible

    ) {
        val context = LocalContext.current
        Column() {
            if (windowSize.width == WindowType.Expanded) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = quakeState.value.title)
                            Text(text = quakeState.value.country)
                            Text(text = quakeState.value.place)
                        }

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = quakeState.value.date)
                            Text(text = quakeState.value.magnitude)
                            Text(text = quakeState.value.tsunami)
                            Text(text = quakeState.value.depth)
                        }
                    }

                    Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                        Button(onClick = {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(quakeState.value.url))
                            context.startActivity(intent)
                        }) {
                            Text(text = "View online")
                        }
                        Button(onClick = {
                            navController.navigate(Screen.MainScreen.route)
                            {
                                popUpTo(0)
                            }
//                            Crashes.generateTestCrash()
//                            Analytics.trackEvent("Refresh button was clicked !")
                        }) {
                            Text(text = "Refresh")
                        }
                    }
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {




                    Text(text = quakeState.value.title)
                    var dateArray = formatDate(quakeState.value.date)
                    Text(text = dateArray[0])
                    Text(text = dateArray[1])
                    Text(text = "Magnitude : " + quakeState.value.magnitude)
                    Text(text = "Tsunami warning: " + quakeState.value.tsunami)
                    Text(text = "Depth: " + quakeState.value.depth)
                    Text(text = "Country: " + quakeState.value.country)
                    Text(text = "Location : " + quakeState.value.place)

                    Row(horizontalArrangement = Arrangement.SpaceAround) {


                        Button(onClick = {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(quakeState.value.url))
                            context.startActivity(intent)
                        }) {
                            Text(text = "View online")
                        }
                    }
                    Button(onClick = {
                        scope.launch {
                            if (sheetState.isCollapsed) {
                                sheetState.expand()
                            } else {
                                sheetState.collapse()
                            }
                        }
                    }) {
                        Text(text = "Show More")
                    }
                    Button(onClick = {
                        navController.navigate(Screen.MainScreen.route){
                            popUpTo(0)
                        }
                    }) {
                        Text(text = "Refresh")
                    }
                }
            }
        }
    }
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

@Preview
@Composable
fun defaultPreview() {

}
/** PLAN
 * Main screen should have Navigation drawer at the top with buttons for "this screen" , "custom get request" , "map screen" , "app options"
 * At the bottom there should be a BottomSheet that contains 10 more latest earthquakes, clicking on one will display it as the main feature in the middle of the screen
 * Middle of the screen will auto display the details of the last earthquake, it will also contain the button that will launch google map composable screen , add to favorite list button too
 * Custom get request will give user the options to query more specific data about earthquakes with provided options like time frame , country , magnitude , number of results etc
 * Map screen will show where the latest or the clicked earthquake happened
 * App options will have some minor options like notification when a new earthquake happens , etc
 */