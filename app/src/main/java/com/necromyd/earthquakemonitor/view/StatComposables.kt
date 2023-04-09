package com.necromyd.earthquakemonitor.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.necromyd.earthquakemonitor.viewmodel.EarthquakeViewModel

@Composable
fun Top5InLast24h(viewModel: EarthquakeViewModel) {
    val topFiveToday = viewModel.getTop5Today()
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        backgroundColor = MaterialTheme.colors.primary,
        elevation = 8.dp,
        shape = RoundedCornerShape(14.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Top 5 in the last 24h",
                color = MaterialTheme.colors.background,
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(10.dp))
            for (quake in topFiveToday) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { viewModel.selectedQuakeToPreview(quake) },
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = quake.magnitude,
                        fontSize = 20.sp,
                        color = viewModel.getMagnitudeColor(quake.magnitude.toDouble())
                    )
                    Text(
                        text = quake.place,
                        fontSize = 16.sp,
                        color = MaterialTheme.colors.background
                    )
                }

            }
        }
    }
}