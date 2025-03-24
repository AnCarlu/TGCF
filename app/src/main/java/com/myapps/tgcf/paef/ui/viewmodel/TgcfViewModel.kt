package com.myapps.tgcf.paef.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.myapps.tgcf.paef.data.ScoreTables
import javax.inject.Inject

class TgcfViewModel @Inject constructor() : ViewModel() {


    var isMale by mutableStateOf(true) //Estado para controlar el sexo marcado
    var showAgeDialog by mutableStateOf(false) //Estado para controlar la visibilidad del Dialogo Edad
        private set
    var pushCount by mutableIntStateOf(0)//Repeticiones de las flexiones
    var pushPoint by mutableIntStateOf(0)//Puntos de las flexiones
    var absCount by mutableStateOf(0)//Repeticiones de los abdominales
    var absPoint by mutableStateOf(0)//Puntos de los abdominales
    var speedTime by mutableStateOf(0.0)//Tiempo de velocidad
    var speedPoint by mutableStateOf(0) // Puntos de velocidad
    var runTime by mutableStateOf(0)//Tiempo en la carrera
    var runPoint by mutableStateOf(0)//Puntos en la carrera
    var pushUpRange by mutableStateOf(0 to 0)//Rango de flexiones
    var absRange by mutableStateOf(0 to 0)//Rango de abdominales
    var speedRange by mutableStateOf(0.0 to 0.0)//Rango de velocidad
    var runRange by mutableStateOf(0 to 0)//Rango de la carrera

    var _ageInput by mutableStateOf(0)
    var ageInput: Int
        get() = _ageInput
        set(value) {
            _ageInput = value
            calculateFinalScore()
        }

    private var ageGroup = 0

    //Livedata para mostrar u ocultar las celdas en caso de que esté marcado APL
    private val _showApl = MutableLiveData<Boolean>()
    val showApl: LiveData<Boolean> = _showApl

    fun onShowApl() {
        _showApl.value = true
    }

    fun onCoverUpApl() {
        _showApl.value = false
    }

    //Estado temporal para el Slider de Edad
    private var _ageDialog by mutableStateOf(0)
    val ageDialog: Int get() = _ageDialog

    //Funcion para mostrar el Dialog de Edad
    fun openAgeDialog() {
        showAgeDialog = true
        _ageDialog = if (ageInput in 17..61) ageInput else 17

    }

    //Funcion para cerrar el Dialog de Edad
    fun closeAgeDialog() {
        showAgeDialog = false
    }

    //Funcion para confirmar la seleccion de Edad
    fun confirmAgeSelect() {
        ageInput = _ageDialog
        calculateFinalScore()
        closeAgeDialog()
    }

    //Funcion para actualizar el valor del Slider de Edad
    fun updateAgeTemplete(value: Float) {
        _ageDialog = value.toInt()
    }

    //Calcular el rango de edad para las tablas de los ejercicios
    fun calculateFinalScore() {
        ageGroup = when (ageInput) {
            in 17..21 -> 1
            in 22..26 -> 2
            in 27..31 -> 3
            in 32..36 -> 4
            in 37..41 -> 5
            in 42..46 -> 6
            in 47..49 -> 7
            in 50..51 -> 8
            in 52..53 -> 9
            in 54..55 -> 10
            in 56..57 -> 11
            in 58..59 -> 12
            in 60..61 -> 13
            else -> 14
        }
        updateAllRanges()
        updateAllScores()
    }

    //Funcion para actualizar los rangos cuando se actualiza tanto la edad como el genero
    fun updateAllRanges() {
        pushUpRange = ScoreTables.getPushUpRange(ageGroup, isMale)
        absRange = ScoreTables.getAbsRange(ageGroup, isMale)
        speedRange=ScoreTables.getSpeedRange(ageGroup,isMale)
        runRange = ScoreTables.getRunRange(ageGroup, isMale)

        speedTime=speedRange.second

    }

    //Funcion para actualizar al nota cuando se actualiza tanto la edad, el genero como las repeticiones
    fun updateAllScores() {
        pushPoint = ScoreTables.getPushUpScore(ageGroup, isMale, pushCount)
        absPoint = ScoreTables.getAbsScore(ageGroup, isMale, absCount)
        runPoint=ScoreTables.getRunScore(ageGroup,isMale,runTime)
        speedPoint = ScoreTables.getSpeedScore(ageGroup, isMale, speedTime)
    }



}