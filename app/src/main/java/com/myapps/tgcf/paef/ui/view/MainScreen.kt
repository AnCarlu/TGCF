package com.myapps.tgcf.paef.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myapps.tgcf.R
import kotlin.math.roundToInt

@Preview
@Composable
fun Screen() {
    Column(modifier = Modifier.fillMaxSize()) {
        Result(
            Modifier
                .weight(1f)
                .padding(4.dp)
        )
        Row(
            Modifier
                .weight(1f)
                .padding(4.dp)
        ) {
            Push(
                Modifier
                    .weight(1f)
                    .padding(4.dp)
            )
            Abs(
                Modifier
                    .weight(1f)
                    .padding(4.dp)
            )
        }
        Row(
            Modifier
                .weight(1f)
                .padding(4.dp),
        ) {
            Speed(
                Modifier
                    .weight(1f)
                    .padding(4.dp)
            )
            Resistance(
                Modifier
                    .weight(1f)
                    .padding(4.dp)
            )
        }

    }
}

@Composable
fun Result(modifier: Modifier) {
    Card(
        modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.primary_color))
    ) {
        Text("Resultado de las pruebas")
    }

}

@Composable
fun Push(modifier: Modifier) {
    var myText by remember { mutableStateOf("0") }
    var sliderPosition by remember { mutableStateOf(0f) }
    Card(
        modifier
            .fillMaxSize(),
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.success_color))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            Text(
                "Flexiones",
                style = TextStyle(
                    color = colorResource(id = R.color.text_primary),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.align(Alignment.TopCenter)

            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            TextField(
                value = myText,
                onValueChange = { newValue ->
                    myText = newValue
                    newValue.toIntOrNull()?.let { intValue ->
                        sliderPosition = intValue.toFloat()
                    }
                },
                modifier = Modifier.size(55.dp),
                maxLines = 1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Slider(value = sliderPosition,
                onValueChange = { newValue ->
                    val rounValue = newValue.roundToInt().toFloat()
                    sliderPosition=rounValue
                    myText = newValue.toInt().toString()
                },
                modifier= Modifier.padding(start = 24.dp, end = 24.dp),
                valueRange = 0f..10f,
                steps = 9
            )
        }
    }
}

@Composable
fun Abs(modifier: Modifier) {
    Card(
        modifier
            .fillMaxSize(), colors = CardDefaults.cardColors(Color.Red)
    ) {
        Text("Flexiones")
    }
}

@Composable
fun Speed(modifier: Modifier) {
    Card(
        modifier
            .fillMaxSize(), colors = CardDefaults.cardColors(Color.Red)
    ) {
        Text("Flexiones")
    }
}

@Composable
fun Resistance(modifier: Modifier) {
    Card(
        modifier
            .fillMaxSize(), colors = CardDefaults.cardColors(Color.Red)
    ) {
        Text("Flexiones")
    }
}


