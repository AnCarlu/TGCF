package com.myapps.tgcf.paef.ui.view

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog


@Composable
fun AgeDialog(
    onDismiss: () -> Unit,
    age: Int,
    onConfirm: () -> Unit,
    onAgeChanged: (Float) -> Unit
) {

    Dialog(onDismissRequest = { onDismiss() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .border(
                    width = 4.dp, color = borderColor, shape = RoundedCornerShape(40.dp)
                ),
            shape = RoundedCornerShape(40.dp),
            colors = CardDefaults.cardColors(containerColor = primaryColor)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    "Añade tu edad",
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 16.dp),
                    color = textPrimaryColor,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )

                TextField(
                    value = age.toString(),
                    onValueChange = { newValue ->
                        newValue.toIntOrNull()?.let { intValue ->
                            val clampedValue = intValue.coerceIn(17, 65)
                            onAgeChanged(clampedValue.toFloat())
                        }
                    },
                    modifier = Modifier.size(55.dp),
                    maxLines = 1,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = primaryColor,
                        unfocusedContainerColor = primaryColor,
                        focusedTextColor = textPrimaryColor,
                        unfocusedTextColor = textPrimaryColor,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
                Spacer(Modifier.size(8.dp))
                Slider(
                    value = age.toFloat().coerceIn(17f..65f),
                    onValueChange = onAgeChanged,
                    valueRange = 17f..65f,
                    modifier = Modifier.padding(start = 24.dp, end = 24.dp),
                    steps = 44,
                    colors = SliderDefaults.colors(
                        thumbColor = thumbColor, //Color del circulo deslizable
                        activeTrackColor = secondaryColor, //Color de la parte activa del Slider
                        inactiveTrackColor = Color.LightGray //Color de la parte desactivada del slider
                    )
                )

                Spacer(Modifier.size(12.dp))
                Row(
                    Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                        .padding(16.dp)
                ) {
                    //Boton de cancelar la accion
                    Button(
                        onClick = { onDismiss() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = cancelColor
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .testTag("ButtonCancel1")
                    ) {
                        Text(
                            text = "Cancelar",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = textPrimaryColor
                        )
                    }
                    Spacer(Modifier.padding(16.dp))
                    //Boton para
                    Button(
                        onClick = onConfirm,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = successColor,
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                    ) {
                        Text(
                            text = "Añadir",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = textPrimaryColor
                        )
                    }
                }
            }
        }
    }
}
