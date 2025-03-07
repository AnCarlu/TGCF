package com.myapps.tgcf.paef.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
fun AgeDialog(
    show: Boolean,
    onDismiss: () -> Unit,
    age: Int,
    onConfirm: () -> Unit,
    onAgeChanged: (Float) -> Unit
) {
    if (show) {
        Dialog(onDismissRequest = { onDismiss() }) {
            Column {

                Text(
                    "Añade tu edad",
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.size(16.dp))
                Slider(
                    value = age.toFloat().coerceIn(17f..61f),
                    onValueChange = onAgeChanged,
                    valueRange = 17f..61f,
                    steps = 44
                )

                Spacer(Modifier.size(16.dp))
                Row(
                    Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                ) {
                    //Boton de cancelar la accion
                    Button(
                        onClick = { onDismiss() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Red
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .testTag("ButtonCancel1")
                    ) {
                        Text(text = "Cancelar", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                    //Boton para
                    Button(
                        onClick = onConfirm,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Blue,
                            disabledContainerColor = Color.Gray
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                    ) {
                        Text(text = "Añadir", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}