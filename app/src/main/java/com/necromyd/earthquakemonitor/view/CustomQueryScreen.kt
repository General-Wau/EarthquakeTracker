package com.necromyd.earthquakemonitor.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Surface
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.InternalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.necromyd.earthquakemonitor.Screen

@Composable
fun CustomQueryScreenComposable(modifier: Modifier = Modifier.fillMaxSize()) {
    Column(verticalArrangement = Arrangement.SpaceEvenly) {
        DateField()
        CountryField()
        AreaField()
        MagnitudeField()
        DepthField()
        TsunamiField()
    }

}

@Composable
fun DateField() {
    var textValue by remember { mutableStateOf(TextFieldValue("")) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Date : ")
        Box(Modifier.background(Color.White)){
            Text(text = "From : ")
            BasicTextField(
                value = textValue,
                modifier = Modifier
                    .padding(16.dp),
                textStyle = TextStyle(
                    color = Color.Blue,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textDecoration = TextDecoration.Underline
                ),
                onValueChange = {
                    textValue = it
                }
            )
        }
        Box(Modifier.background(Color.White)){
            Text(text = "To : ")
            BasicTextField(
                value = textValue,
                modifier = Modifier
                    .padding(16.dp),
                textStyle = TextStyle(
                    color = Color.Blue,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textDecoration = TextDecoration.Underline
                ),
                onValueChange = {
                    textValue = it
                }
            )
        }
    }
}

@Composable
fun MagnitudeField() {
    var textValue by remember { mutableStateOf(TextFieldValue("")) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Magnitude : ")
        BasicTextField(
            value = textValue,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            textStyle = TextStyle(
                color = Color.Blue,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline
            ),
            onValueChange = {
                textValue = it
            }
        )
    }
}

@Composable
fun CountryField() {
    var textValue by remember { mutableStateOf(TextFieldValue("")) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Place : ")
        BasicTextField(
            value = textValue,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            textStyle = TextStyle(
                color = Color.Blue,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline
            ),
            onValueChange = {
                textValue = it
            }
        )
    }
}

@Composable
fun TsunamiField() {
    var textValue by remember { mutableStateOf(TextFieldValue("")) }
    val checkedState = remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Tsunami Warning : ")
        Switch(
            checked = checkedState.value,
            onCheckedChange = { checkedState.value = it }
        )

    }
}

@Composable
fun DepthField() {
    var textValue by remember { mutableStateOf(TextFieldValue("")) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Depth : ")
        BasicTextField(
            value = textValue,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            textStyle = TextStyle(
                color = Color.Blue,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline
            ),
            onValueChange = {
                textValue = it
            }
        )
    }
}

@Composable
fun AreaField() {
    var textValue by remember { mutableStateOf(TextFieldValue("")) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Area : ")
        BasicTextField(
            value = textValue,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            textStyle = TextStyle(
                color = Color.Blue,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline
            ),
            onValueChange = {
                textValue = it
            }
        )
    }
}


@Preview
@Composable
fun previewCustom() {
    CustomQueryScreenComposable()
}