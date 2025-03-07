package com.myapps.tgcf.paef.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myapps.tgcf.R
import com.myapps.tgcf.paef.ui.viewmodel.TgcfViewModel
import kotlin.math.roundToInt

val primaryColor = Color(0xFF7689A9)
val secondaryColor = Color(0xFF2D4671)
val backIconColor = Color(0xFFAA6939)
val backgroundColor = Color(0xFFFFFFFF)
val borderColor = Color(0xFF152C55)
val textPrimaryColor = Color(0xFF000000)
val thumbColor = Color(0xFFAA9539)
val successColor = Color(0xFF4CAF50)
val warningColor = Color(0xFFFFC107)
val errorColor = Color(0xFFFF5252)
val accentColor = Color(0xFFFF4081)

@Preview
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
            }
        )
        Row(
            Modifier
                .weight(1f)
                .padding(4.dp)
        ) {
            //Card de flexiones
            Push(
                Modifier
                    .weight(1f)
                    .padding(4.dp),
                tgfcViewModel
            )
            //Card de abdominales
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
            if (!showApl) {
                //Card de velocidad
                Speed(
                    Modifier
                        .weight(1f)
                        .padding(4.dp)
                )
                //Card de carrera
                Resistance(
                    Modifier
                        .weight(1f)
                        .padding(4.dp)
                )
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
    var resutl by remember { mutableStateOf("") }
    var showPlaceholder by remember { mutableStateOf(true) } //Variable para mostrar la palabra "Edad" si la edad está vacia
    var isManSelected by remember { mutableStateOf(true) } //Variable para cambiar el color del sexo

    //Diaogo para elegir la edad
    AgeDialog(
        show = viewModel.showAgeDialog,
        onDismiss = { viewModel.closeAgeDialog() },
        age = viewModel.ageDialog,
        onConfirm = { viewModel.confirmAgeSelect() },
        onAgeChanged = {viewModel.updateAgeTemplete(it)})
    Card(
        modifier
            .fillMaxWidth()
            .shadow(4.dp, shape = RoundedCornerShape(8.dp))
            .border(
                width = 4.dp,
                color = borderColor,
                shape = RoundedCornerShape(8.dp)
            ),
        colors = CardDefaults.cardColors(containerColor = primaryColor)
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
                        Icon(
                            painter = painterResource(R.drawable.man),
                            contentDescription = "Man",
                            modifier = Modifier
                                .size(96.dp)
                                .clickable {
                                    isManSelected = true
                                    viewModel.isMale = true
                                    viewModel.updateAllRanges()
                                }
                                .alpha(if (isManSelected) 1f else 0.8f),
                            tint = if (isManSelected) backIconColor else Color.Gray
                        )
                        //Icono Mujer
                        Icon(
                            painter = painterResource(R.drawable.woman),
                            contentDescription = "Woman",
                            modifier = Modifier
                                .size(96.dp)
                                .clickable {
                                    isManSelected = false
                                    viewModel.isMale = false
                                    viewModel.updateAllRanges()
                                }
                                .alpha(if (isManSelected) 0.8f else 1f),
                            tint = if (!isManSelected) backIconColor else Color.Gray
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(start = 24.dp, bottom = 8.dp)
                ) {
                    Column(modifier.fillMaxWidth()) {

                        TextField(
                            value = if (viewModel.ageInput == 0) "" else viewModel.ageInput.toString(),
                            onValueChange = {},
                            placeholder = {
                                //Si no se ha añadido aun la edad se muestra la palabra edad
                                if (showPlaceholder) {
                                    Text(
                                        "Edad",
                                        style = TextStyle(
                                            fontStyle = FontStyle.Italic,
                                            fontSize = 16.sp,
                                            fontFamily = FontFamily.SansSerif,
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                }
                            },
                            modifier = Modifier
                                .clickable {
                                    viewModel.openAgeDialog() //Abre el Dialogo de edad al pulsar
                                }
                                .onFocusChanged {
                                    if (it.isFocused) {
                                        showPlaceholder = false
                                    }
                                }
                                .size(width = 100.dp, height = 60.dp),
                            readOnly = true,
                            //singleLine = true,
                            //keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = primaryColor,
                                unfocusedContainerColor = primaryColor,
                                focusedTextColor = textPrimaryColor,
                                unfocusedTextColor = textPrimaryColor,
                                unfocusedIndicatorColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent
                            )
                        )

                        Spacer(Modifier.padding(8.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {

                            Text(
                                "APL",
                                style = TextStyle(
                                    color = textPrimaryColor,
                                    fontSize = 16.sp,
                                    fontStyle = FontStyle.Italic,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Spacer(Modifier.padding(8.dp))
                            //CheckBox para marcar si es APL
                            Checkbox(
                                checked = isAplChecked,
                                onCheckedChange = onAplCheckedChanged
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
                            value = resutl,
                            onValueChange = { resutl = it },
                            modifier = Modifier.size(55.dp),
                            maxLines = 1,
                            readOnly = true,
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


//Funcion para controlar la Card de las flexiones
@Composable
fun Push(modifier: Modifier, viewModel: TgcfViewModel) {
    val range = viewModel.pushUpRange  //Rando de las flexiones
    var myText by remember { mutableStateOf(viewModel.pushCount.toString()) } //Variable que recoge el numero del TextField
    var sliderPosition by remember { mutableStateOf(range.first.toFloat()) } //Posicion del Slider
    Card(
        modifier
            .fillMaxSize()
            .border(
                width = 4.dp,
                color = borderColor,
                shape = RoundedCornerShape(10.dp)
            ),
        colors = CardDefaults.cardColors(containerColor = primaryColor)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            //Box para colocar la Imagen y el Text con el nombre de la Card
            Box(
                modifier = Modifier
                    .background(primaryColor)
                    .align(Alignment.Center)
                    .padding(top = 10.dp)
            ) {
                //Imagen del deporte de la Card
                Image(
                    painter = painterResource(id = R.drawable.push_up),
                    contentDescription = "push up"
                )
            }
            Text(
                "Flexiones",
                style = TextStyle(
                    color = textPrimaryColor,
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
            /*

             */
            TextField(
                value = sliderPosition.toInt().toString(),
                onValueChange = { newValue ->
                    sliderPosition = newValue.toFloat()
                    viewModel.pushCount = newValue.toIntOrNull() ?: 0
                    newValue.toIntOrNull()?.let { intValue ->
                        sliderPosition = intValue.toFloat()
                    }
                },
                modifier = Modifier.size(55.dp),
                maxLines = 1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = primaryColor,
                    unfocusedContainerColor = primaryColor,
                    focusedTextColor = textPrimaryColor,
                    unfocusedTextColor = textPrimaryColor,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
            Slider(
                value = sliderPosition,
                onValueChange = { newValue ->
                    val rounValue = newValue.roundToInt().toFloat()
                    sliderPosition = rounValue
                    myText = newValue.toInt().toString()
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

@Composable
fun Abs(modifier: Modifier) {
    var myText by remember { mutableStateOf("") }
    var sliderPosition by remember { mutableStateOf(0f) }

    Card(
        modifier
            .fillMaxSize()
            .border(
                width = 4.dp,
                color = borderColor,
                shape = RoundedCornerShape(8.dp)
            ),
        colors = CardDefaults.cardColors(containerColor = primaryColor)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .background(primaryColor)
                    .align(Alignment.Center)
                    .padding(top = 15.dp)
            ) {

                Image(
                    painter = painterResource(id = R.drawable.abs),
                    contentDescription = "push up"
                )
            }
            Text(
                "Abdominales",
                style = TextStyle(
                    color = textPrimaryColor,
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
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = primaryColor,
                    unfocusedContainerColor = primaryColor,
                    focusedTextColor = textPrimaryColor,
                    unfocusedTextColor = textPrimaryColor,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
            Slider(
                value = sliderPosition,
                onValueChange = { newValue ->
                    val rounValue = newValue.roundToInt().toFloat()
                    sliderPosition = rounValue
                    myText = newValue.toInt().toString()
                },
                modifier = Modifier.padding(start = 24.dp, end = 24.dp),
                valueRange = 0f..10f,
                steps = 9,
                colors = SliderDefaults.colors(
                    thumbColor = thumbColor, //Color del circulo deslizable
                    activeTrackColor = secondaryColor, //Color de la parte activa del Slider
                    inactiveTrackColor = Color.LightGray //Color de la parte desactivada del slider
                )
            )
        }
    }
}

@Composable
fun Speed(modifier: Modifier) {
    var myText by remember { mutableStateOf("") }
    var sliderPosition by remember { mutableStateOf(0f) }
    Card(
        modifier
            .fillMaxSize()
            .border(
                width = 4.dp,
                color = borderColor,
                shape = RoundedCornerShape(8.dp)
            ),
        colors = CardDefaults.cardColors(containerColor = primaryColor)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .background(primaryColor)
                    .align(Alignment.Center)
                    .padding(top = 10.dp)
            ) {

                Image(
                    painter = painterResource(id = R.drawable.speed),
                    contentDescription = "push up"
                )
            }
            Text(
                "Velocidad",
                style = TextStyle(
                    color = textPrimaryColor,
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
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = primaryColor,
                    unfocusedContainerColor = primaryColor,
                    focusedTextColor = textPrimaryColor,
                    unfocusedTextColor = textPrimaryColor,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
            Slider(
                value = sliderPosition,
                onValueChange = { newValue ->
                    val rounValue = newValue.roundToInt().toFloat()
                    sliderPosition = rounValue
                    myText = newValue.toInt().toString()
                },
                modifier = Modifier.padding(start = 24.dp, end = 24.dp),
                valueRange = 0f..10f,
                steps = 9,
                colors = SliderDefaults.colors(
                    thumbColor = thumbColor, //Color del circulo deslizable
                    activeTrackColor = secondaryColor, //Color de la parte activa del Slider
                    inactiveTrackColor = Color.LightGray //Color de la parte desactivada del slider
                )
            )
        }
    }
}

@Composable
fun Resistance(modifier: Modifier) {
    var myText by remember { mutableStateOf("") }
    var sliderPosition by remember { mutableStateOf(0f) }
    Card(
        modifier
            .fillMaxSize()
            .border(
                width = 4.dp,
                color = borderColor,
                shape = RoundedCornerShape(8.dp)
            ),
        colors = CardDefaults.cardColors(containerColor = primaryColor)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .background(primaryColor)
                    .align(Alignment.Center)
                    .padding(top = 10.dp)
            ) {

                Image(
                    painter = painterResource(id = R.drawable.resistance),
                    contentDescription = "push up"
                )
            }
            Text(
                "Resistencia",
                style = TextStyle(
                    color = textPrimaryColor,
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
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = primaryColor,
                    unfocusedContainerColor = primaryColor,
                    focusedTextColor = textPrimaryColor,
                    unfocusedTextColor = textPrimaryColor,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
            Slider(
                value = sliderPosition,
                onValueChange = { newValue ->
                    val rounValue = newValue.roundToInt().toFloat()
                    sliderPosition = rounValue
                    myText = newValue.toInt().toString()
                },
                modifier = Modifier.padding(start = 24.dp, end = 24.dp),
                valueRange = 0f..10f,
                steps = 9,
                colors = SliderDefaults.colors(
                    thumbColor = thumbColor, //Color del circulo deslizable
                    activeTrackColor = secondaryColor, //Color de la parte activa del Slider
                    inactiveTrackColor = Color.LightGray //Color de la parte desactivada del slider
                )
            )
        }
    }
}


