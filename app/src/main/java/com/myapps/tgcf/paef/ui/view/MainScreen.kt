package com.myapps.tgcf.paef.ui.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.myapps.tgcf.R
import com.myapps.tgcf.paef.data.ScoreTables
import com.myapps.tgcf.paef.ui.viewmodel.TgcfViewModel
import kotlin.math.roundToInt

val primaryColor = Color(0xFF7689A9)
val secondaryColor = Color(0xFF2D4671)
val backIconColor = Color(0xFFAA6939)
val borderColor = Color(0xFF152C55)
val textPrimaryColor = Color(0xFF000000)
val thumbColor = Color(0xFFAA9539)
val successColor = Color(0xFF4CAF50)
val cancelColor = Color(0xFFEF5350)
val errorColor = Color(0xFFFF5252)

@Composable
fun Screen(modifier: Modifier, tgfcViewModel: TgcfViewModel) {

    /*
    Variable para mostrar o no las card de velocidad y Carrera según si está apl marcado o no
     */
    val showApl: Boolean by tgfcViewModel.showApl.observeAsState(false)

    Column(modifier.fillMaxSize()) {
        //Card del Sexo, Edad y el resultado de la nota
        Result(
            Modifier
                .weight(1f)
                .padding(start = 8.dp, top = 16.dp, end = 8.dp, bottom = 4.dp),
            tgfcViewModel,
            isAplChecked = showApl,
            onAplCheckedChanged = { checked ->
                //Si APL se marca, se ocultan las cards de Carrera y Velocidad
                if (checked) {
                    tgfcViewModel.onShowApl()
                } else {
                    tgfcViewModel.onCoverUpApl()
                }
            })
        Row(
            Modifier
                .weight(1f)
                .padding(2.dp)
        ) {
            //Card de flexiones
            Push(
                Modifier
                    .weight(1f)
                    .padding(2.dp), tgfcViewModel
            )
            //Card de abdominales
            Abs(
                Modifier
                    .weight(1f)
                    .padding(2.dp), tgfcViewModel
            )
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(2.dp)
        ) {
            this@Column.AnimatedVisibility(
                visible = !showApl,
                enter = fadeIn() + expandVertically(expandFrom = Alignment.Top),
                exit = fadeOut() + shrinkVertically(shrinkTowards = Alignment.Top),
                modifier = Modifier.fillMaxSize()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    // Card de velocidad
                    Speed(
                        modifier = Modifier
                            .weight(1f)
                            .padding(2.dp),
                        viewModel = tgfcViewModel
                    )

                    // Card de carrera
                    Resistance(
                        modifier = Modifier
                            .weight(1f)
                            .padding(2.dp),
                        viewModel = tgfcViewModel
                    )
                }
            }

        }
    }
}

@Composable
fun Result(
    modifier: Modifier,
    viewModel: TgcfViewModel,
    isAplChecked: Boolean,
    onAplCheckedChanged: (Boolean) -> Unit
) {

    var isManSelected by remember { mutableStateOf(true) } //Variable para cambiar el color del sexo
    // Calculo de la nota final
    if (isAplChecked) {
        viewModel.result =
            ((viewModel.speedPoint.toDouble() + viewModel.runPoint.toDouble()) / 2) / 10
    } else {

        viewModel.result =
            (((viewModel.pushPoint.toDouble() + viewModel.absPoint.toDouble()) +
                    viewModel.speedPoint.toDouble() + viewModel.runPoint.toDouble()) / 4) / 10
    }

    Card(
        modifier
            .fillMaxWidth()
            .shadow(4.dp, shape = RoundedCornerShape(8.dp))
            .border(
                width = 4.dp, color = borderColor, shape = RoundedCornerShape(8.dp)
            ), colors = CardDefaults.cardColors(containerColor = primaryColor)
    ) {
        Row {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                Box(modifier = Modifier.padding(top = 16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        //Icono Hombre
                        Icon(painter = painterResource(R.drawable.man),
                            contentDescription = "Man",
                            modifier = Modifier
                                .size(96.dp)
                                .clickable {
                                    isManSelected = true
                                    viewModel.isMale = true
                                    viewModel.updateAllRanges()
                                    viewModel.updateAllScores()
                                }
                                .alpha(if (isManSelected) 1f else 0.8f),
                            tint = if (isManSelected) backIconColor else Color.Gray)
                        //Icono Mujer
                        Icon(painter = painterResource(R.drawable.woman),
                            contentDescription = "Woman",
                            modifier = Modifier
                                .size(96.dp)
                                .clickable {
                                    isManSelected = false
                                    viewModel.isMale = false
                                    viewModel.updateAllRanges()
                                    viewModel.updateAllScores()
                                }
                                .alpha(if (isManSelected) 0.8f else 1f),
                            tint = if (!isManSelected) backIconColor else Color.Gray)
                    }
                }

                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(start = 24.dp, bottom = 8.dp)
                ) {
                    Column(modifier.fillMaxWidth()) {

                        Age(viewModel)

                        Spacer(Modifier.padding(8.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {

                            Text(
                                "APL", style = TextStyle(
                                    color = textPrimaryColor,
                                    fontSize = 16.sp,
                                    fontStyle = FontStyle.Italic,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Spacer(Modifier.padding(8.dp))
                            //CheckBox para marcar si es APL
                            Checkbox(
                                checked = isAplChecked, onCheckedChange = onAplCheckedChanged
                            )
                        }
                    }
                }
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {

                Box(modifier = Modifier.align(Alignment.Center)) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            "Nota Final",
                            style = TextStyle(
                                color = textPrimaryColor,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Serif,
                                textAlign = TextAlign.Center,
                                fontStyle = FontStyle.Italic
                            ),
                        )
                        Spacer(Modifier.padding(16.dp))

                        TextField(
                            value = viewModel.result.toString(),
                            onValueChange = { },
                            modifier = Modifier.size(120.dp),
                            maxLines = 1,
                            readOnly = true,
                            textStyle = TextStyle(
                                fontSize = 40.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Serif,
                                textAlign = TextAlign.Center,
                                fontStyle = FontStyle.Italic
                            ),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = primaryColor,
                                unfocusedContainerColor = primaryColor,
                                focusedTextColor = textPrimaryColor,
                                unfocusedTextColor = textPrimaryColor,
                                unfocusedIndicatorColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent
                            )
                        )
                    }
                }
            }
        }

    }
}


@Composable
fun Age(viewModel: TgcfViewModel) {
    var showPlaceholder by remember { mutableStateOf(true) } //Estado para mostrar la palabra "Edad" si la edad está vacia


    TextField(value = if (viewModel.ageInput == 0) "" else viewModel.ageInput.toString(),
        onValueChange = {},
        placeholder = {
            //Si no se ha añadido aun la edad se muestra la palabra edad
            if (showPlaceholder) {
                Text(
                    "Edad", style = TextStyle(
                        fontStyle = FontStyle.Italic,
                        fontSize = 16.sp,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        },
        modifier = Modifier
            .clickable { viewModel.openAgeDialog() }
            .onFocusChanged {
                if (it.isFocused) {
                    showPlaceholder = false
                }
            }
            .size(width = 70.dp, height = 50.dp),
        readOnly = true,
        enabled = false,
        colors = TextFieldDefaults.colors(
            disabledContainerColor = primaryColor,
            disabledTextColor = textPrimaryColor,
            disabledIndicatorColor = Color.Transparent
        ))

    if (viewModel.showAgeDialog) {
        AgeDialog(
            onDismiss = { viewModel.closeAgeDialog() },
            age = viewModel.ageDialog,
            onConfirm = { viewModel.confirmAgeSelect() },
            onAgeChanged = { viewModel.updateAgeTemplete(it) })
    }
}


@Composable
fun ExerciseCard(
    modifier: Modifier,
    title: String,
    text: String,
    showSliderPosition: Boolean,
    image: Int,
    range: Pair<Int, Int>,
    currentValue: Int,
    onValueChange: (Int) -> Unit
) {
    var sliderPosition by remember { mutableStateOf(range.first.toFloat()) }
    Card(
        modifier
            .fillMaxSize()
            .border(
                width = 4.dp,
                color = if (currentValue < 20) errorColor else borderColor,
                shape = RoundedCornerShape(16.dp)
            ), colors = CardDefaults.cardColors(containerColor = primaryColor),
        shape = RoundedCornerShape(16.dp)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            // Imagen de fondo
            Image(
                painter = painterResource(id = image),
                contentDescription = "background image",
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(0.25f),  // Ajusta transparencia
                contentScale = ContentScale.FillBounds
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    title, style = TextStyle(
                        color = textPrimaryColor,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif,
                        textAlign = TextAlign.Center
                    ), modifier = Modifier.padding(top = 8.dp)
                )

                // Controles interactivos
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    TextField(
                        value = "Puntos:  $currentValue",
                        onValueChange = {},
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 1,
                        readOnly = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = primaryColor.copy(alpha = 0.01f),
                            unfocusedContainerColor = primaryColor.copy(alpha = 0.01f),
                            focusedTextColor = textPrimaryColor,
                            unfocusedTextColor = textPrimaryColor,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        )
                    )

                    Spacer(Modifier.size(16.dp))
                    TextField(
                        value = if (showSliderPosition) {
                            text + sliderPosition.toInt().toString()
                        } else {
                            text
                        },
                        onValueChange = {},
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 1,
                        readOnly = true,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = primaryColor.copy(alpha = 0.01f),
                            unfocusedContainerColor = primaryColor.copy(alpha = 0.01f),
                            focusedTextColor = textPrimaryColor,
                            unfocusedTextColor = textPrimaryColor,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        )
                    )

                    Slider(
                        value = sliderPosition,
                        onValueChange = { newValue ->
                            val rounValue = newValue.roundToInt().toFloat()
                            sliderPosition = rounValue
                            onValueChange(newValue.toInt())
                        },
                        valueRange = range.first.toFloat()..range.second.toFloat(),
                        steps = range.second - range.first,
                        modifier = Modifier.padding(start = 24.dp, end = 24.dp),
                        colors = SliderDefaults.colors(
                            thumbColor = thumbColor, //Color del circulo deslizable
                            activeTrackColor = secondaryColor, //Color de la parte activa del Slider
                            inactiveTrackColor = Color.LightGray //Color de la parte desactivada del slider
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun ExerciseCardSpeed(
    viewModel: TgcfViewModel,
    modifier: Modifier,
    title: String,
    text: String,
    image: Int,
    range: Pair<Double, Double>,
    currentValue: Double,
    onValueChange: (Double) -> Unit
) {
// Calcular posición invertida para el slider
    val invertedPosition = remember(currentValue) {
        (range.first + range.second - currentValue).toFloat()
    }

    var sliderPosition by remember { mutableStateOf(invertedPosition) }

    LaunchedEffect(currentValue) {
        sliderPosition = (range.first + range.second - currentValue).toFloat()
    }
    Card(
        modifier
            .fillMaxSize()
            .border(
                width = 4.dp,
                color = if (viewModel.speedPoint < 20) errorColor else borderColor,
                shape = RoundedCornerShape(16.dp)
            ), colors = CardDefaults.cardColors(containerColor = primaryColor),
        shape = RoundedCornerShape(16.dp)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            // Imagen de fondo
            Image(
                painter = painterResource(id = image),
                contentDescription = "background image",
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(0.25f),  // Ajusta transparencia
                contentScale = ContentScale.FillBounds
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    title, style = TextStyle(
                        color = textPrimaryColor,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif,
                        textAlign = TextAlign.Center
                    ), modifier = Modifier.padding(top = 8.dp)
                )

                // Controles interactivos
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    TextField(
                        value = "Puntos:  ${viewModel.speedPoint}",
                        onValueChange = {},
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 1,
                        readOnly = true,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = primaryColor.copy(alpha = 0.01f),
                            unfocusedContainerColor = primaryColor.copy(alpha = 0.01f),
                            focusedTextColor = textPrimaryColor,
                            unfocusedTextColor = textPrimaryColor,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        )
                    )

                    Spacer(Modifier.size(16.dp))
                    TextField(
                        value = "$text %.1f segundos".format(currentValue),
                        onValueChange = {},
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 1,
                        readOnly = true,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = primaryColor.copy(alpha = 0.01f),
                            unfocusedContainerColor = primaryColor.copy(alpha = 0.01f),
                            focusedTextColor = textPrimaryColor,
                            unfocusedTextColor = textPrimaryColor,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        )
                    )

                    Slider(
                        value = sliderPosition.toFloat(),
                        onValueChange = { newValue ->
                            sliderPosition = newValue
                            onValueChange(newValue.toDouble())
                        },
                        valueRange = range.first.toFloat()..range.second.toFloat(),
                        steps = ((range.second - range.first) / 0.1).toInt(),
                        modifier = Modifier.padding(start = 24.dp, end = 24.dp),
                        colors = SliderDefaults.colors(
                            thumbColor = thumbColor, //Color del circulo deslizable
                            activeTrackColor = secondaryColor, //Color de la parte activa del Slider
                            inactiveTrackColor = Color.LightGray //Color de la parte desactivada del slider
                        )
                    )
                }
            }
        }
    }
}

//Funcion para controlar la Card de las flexiones
@Composable
fun Push(modifier: Modifier, viewModel: TgcfViewModel) {
    ExerciseCard(
        modifier = modifier,
        title = "Flexiones",
        text = "Repeticiones: ",
        showSliderPosition = true,
        image = R.drawable.push_up,
        range = viewModel.pushUpRange,
        currentValue = viewModel.pushPoint,
        onValueChange = {
            viewModel.pushCount = it
            viewModel.updateAllScores()
        }
    )
}

@Composable
fun Abs(modifier: Modifier, viewModel: TgcfViewModel) {
    ExerciseCard(
        modifier = modifier,
        title = "Abdominales",
        text = "Repeticiones: ",
        showSliderPosition = true,
        image = R.drawable.abs,
        range = viewModel.absRange,
        currentValue = viewModel.absPoint,
        onValueChange = {
            viewModel.absCount = it
            viewModel.updateAllScores()
        }
    )
}

@Composable
fun Speed(modifier: Modifier, viewModel: TgcfViewModel) {
    ExerciseCardSpeed(
        viewModel = viewModel,
        modifier = modifier,
        title = "Velocidad",
        text = "Tiempo: ",
        image = R.drawable.speed,
        range = viewModel.speedRange,
        currentValue = viewModel.speedTime,
        onValueChange = { newValue ->
            val invertedValue = viewModel.speedRange.first + viewModel.speedRange.second - newValue
            viewModel.speedTime = invertedValue
            viewModel.updateAllScores()
        }
    )
}

//Funcion para formatear el tiempo para que lo marque en mm:ss
fun formatSecondsToMmSs(seconds: Int): String {
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return String.format("%02d:%02d", minutes, remainingSeconds)
}

@Composable
fun Resistance(modifier: Modifier, viewModel: TgcfViewModel) {

    val formatTime = remember(viewModel.runTime) {
        formatSecondsToMmSs(viewModel.runTime)
    }
    ExerciseCard(
        modifier = modifier,
        title = "6 KM",
        text = "Tiempo: $formatTime ",
        showSliderPosition = false,
        image = R.drawable.resistance,
        range = viewModel.runRange,
        currentValue = viewModel.runPoint,
        onValueChange = { newValue ->
            //Variable para que los peores resultados sean mostrados en la parte izquierda del slider
            val invertedValue =
                viewModel.runRange.second + viewModel.runRange.first - newValue
            viewModel.runTime = invertedValue
            viewModel.updateAllScores()
        }
    )
}





