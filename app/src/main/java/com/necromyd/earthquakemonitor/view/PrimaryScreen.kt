package com.necromyd.earthquakemonitor.view

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.necromyd.earthquakemonitor.BuildConfig
import com.necromyd.earthquakemonitor.BuildConfig.aKey
import com.necromyd.earthquakemonitor.viewmodel.EarthquakeViewModel

@Composable
fun PrimaryScreen(viewModel: EarthquakeViewModel) {

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.onBackground) {
        if (viewModel.stateList.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentSize(Alignment.Center), color = Color.Black
                )
            }
        } else {

            Column(
                modifier = Modifier.verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LatestEarthquake(viewModel)
                RecentEarthquakes(viewModel)
                Top5InLast24h(viewModel)
            }
        }

    }

}

@Composable
fun LatestEarthquake(viewModel: EarthquakeViewModel) {

    val quakeState by viewModel.selectedQuake.observeAsState()
    var expandedState by remember {
        mutableStateOf(false)
    }
    val collapsed = "View less"
    val closed = "View more"
    val context = LocalContext.current
    val url = viewModel.selectedQuake.value?.url
    val intent = remember {
        Intent(
            Intent.ACTION_VIEW, Uri.parse(url)
        )
    }
    val tsunamiWarning = if (viewModel.selectedQuake.value?.tsunami.equals("0")) "no" else "yes"
    val countryNameFormat = viewModel.countryNameFormat()

    val dateTime = quakeState?.let { viewModel.formatDate(it.date) }
    val date = dateTime?.get(0)
    val time = dateTime?.get(1)
    val bgColor =
        viewModel.selectedQuake.value?.magnitude?.let { viewModel.getMagnitudeColor(it.toDouble()) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, start = 12.dp, end = 12.dp, bottom = 7.dp)
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            ),
        backgroundColor = MaterialTheme.colors.primary,
        elevation = 8.dp,
        shape = RoundedCornerShape(14.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "Latest Earthquake : ",
                fontSize = 32.sp,
                color = MaterialTheme.colors.background
            )

            quakeState?.let {
                if (bgColor != null) {
                    Text(
                        text = it.magnitude,
                        fontSize = 45.sp,
                        color = bgColor
                    )
                }
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    if (bgColor != null) {
                        Text(
                            text = "magnitude",
                            fontSize = 14.sp,
                            color = bgColor
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(5.dp))

            quakeState?.let {
                Text(
                    text = countryNameFormat,
                    fontSize = 16.sp,
                    color = MaterialTheme.colors.background
                )
            }
            quakeState?.let {
                Text(
                    text = it.place,
                    fontSize = 16.sp,
                    color = MaterialTheme.colors.background
                )
            }
            if (date != null) {
                Text(
                    text = date,
                    fontSize = 16.sp,
                    color = MaterialTheme.colors.background
                )
            }

            if (time != null) {
                Text(
                    text = time,
                    fontSize = 16.sp,
                    color = MaterialTheme.colors.background
                )
            }

            if (expandedState) {
                quakeState?.let {
                    Text(
                        text = "Depth : " + it.depth + "km",
                        fontSize = 16.sp,
                        color = MaterialTheme.colors.background
                    )
                }

                quakeState?.let {
                    Text(
                        text = "Tsunami warning : $tsunamiWarning",
                        fontSize = 16.sp,
                        color = MaterialTheme.colors.background
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                quakeState?.let { OpenStreetMapSnapshot(it.latitude, it.longitude) }

                TextButton(onClick = {
                    context.startActivity(intent)
                }) {
                    Text(
                        text = "View online",
                        fontSize = 16.sp,
                        color = MaterialTheme.colors.onSecondary
                    )
                }
            }

            TextButton(onClick = { expandedState = !expandedState }) {
                Text(
                    text = if (expandedState) collapsed else closed,
                    fontSize = 20.sp,
                    color = MaterialTheme.colors.onSecondary
                )
            }
        }
    }
}

@Composable
fun OpenStreetMapSnapshot(lat: String, lon: String) {
    val aKey = BuildConfig.aKey
    val zoom = "6"
    val imageUrl = "https://api.mapbox.com/styles/v1/mapbox/streets-v11/static/pin-s-l+000" +
            "($lon,$lat)/$lon,$lat,$zoom,0/300x300?access_token=$aKey"

    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp,
        backgroundColor = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    ) {
        Image(
            painter = rememberImagePainter(
                data = imageUrl,
                builder = {}
            ),
            contentDescription = "Map snapshot",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
    }
}