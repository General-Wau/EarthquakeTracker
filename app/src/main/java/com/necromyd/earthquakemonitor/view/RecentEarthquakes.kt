package com.necromyd.earthquakemonitor.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.necromyd.earthquakemonitor.model.Earthquake
import com.necromyd.earthquakemonitor.viewmodel.EarthquakeViewModel

@Composable
fun RecentEarthquakes(viewModel: EarthquakeViewModel) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        CardRowTitle("Latest Earthquakes", true)
        CardRow(viewModel)
        CardRowTitle(name = "In the past 3 hours : ${viewModel.numberOfQuakesToday()}" , false )

    }
}

@Composable
fun CardRow(viewModel: EarthquakeViewModel) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 15.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        itemsIndexed(viewModel.stateList) { _, item ->
            CardRowItem(viewModel, item)
        }
    }
}

@Composable
fun CardRowTitle(name: String, roundTop : Boolean) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 12.dp, end = 12.dp, top = 9.dp, bottom = 7.dp),
            shape = RoundedCornerShape(
                topStart = if(roundTop) 16.dp else 0.dp,
                topEnd = if(roundTop) 16.dp else 0.dp,
                bottomStart = if(roundTop) 0.dp else 16.dp,
                bottomEnd = if(roundTop) 0.dp else 16.dp,
            ),
        backgroundColor = MaterialTheme.colors.primary,
        elevation = 10.dp

    ) {
        Text(
            text = name,
            fontSize = 22.sp,
            color = MaterialTheme.colors.onSecondary,
            textAlign = TextAlign.Center
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CardRowItem(viewModel: EarthquakeViewModel, quake: Earthquake) {
    val contextLocal = LocalContext.current

    val magnitude = quake.magnitude.toDouble()
    val bgColor = viewModel.getMagnitudeColor(magnitude)

    Card(
        modifier = Modifier
            .width(150.dp)
            .height(130.dp)
            .verticalScroll(rememberScrollState()),
        shape = RoundedCornerShape(8.dp),
        backgroundColor = bgColor,
        elevation = 3.dp,
        onClick = {
            viewModel.selectedQuakeToPreview(quake)
            viewModel.mToast(contextLocal)
        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center, modifier = Modifier.padding(10.dp)
        ) {
            Text(text = quake.magnitude, fontSize = 25.sp)
            Text(text = quake.place, fontSize = 15.sp, textAlign = TextAlign.Center)
            Text(text = viewModel.formatDate(quake.date)[1], fontSize = 15.sp)
        }
    }
}