package com.necromyd.earthquakemonitor.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import com.necromyd.earthquakemonitor.WindowSize
import com.necromyd.earthquakemonitor.WindowType
import com.necromyd.earthquakemonitor.model.Earthquake

@Composable
fun EarthquakeDisplay(windowSize: WindowSize, earthquake: Earthquake) {
    
    if (windowSize.width == WindowType.Expanded) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = earthquake.title)
                Text(text = earthquake.country)
                Text(text = earthquake.place)
            }
            
            Column(
                modifier = Modifier
                    .fillMaxSize(),
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
        }
    }
}

@Composable
fun NavigationMenu() {
    
}

/** PLAN
 * Main screen should have Navigation drawer at the top with buttons for "this screen" , "custom get request" , "saved quakes list" , "app options"
 * At the bottom there should be a BottomSheet that contains 10 more latest earthquakes, clicking on one will display it as the main feature in the middle of the screen
 * Middle of the screen will auto display the details of the last earthquake, it will also contain the button that will launch google map composable screen , add to favorite list button too
 * Custom get request will give user the options to query more specific data about earthquakes with provided options like time frame , country , magnitude , number of results etc
 * Saved quakes list will display all earthquakes that have been tagged by the user as a lazy list that expands a row vertically with more info when clicked
 * App options will have some minor options like notification when a new earthquake happens , etc
 */