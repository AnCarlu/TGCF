package com.myapps.tgcf.paef.ui.viewmodel

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
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
    var ageInput by mutableIntStateOf(17) //Valor de edad, inicializado a 17
    var isAplChecked by mutableStateOf(false) //Estado para controlar si se ha marcado APL

    var showAgeDialog by mutableStateOf(false) //Estado para controlar la visibilidad del Dialogo Edad
        private set

    //Repeticiones por prueba
    var pushCount by mutableIntStateOf(0)//Repeticiones de las flexiones
    var absCount by mutableIntStateOf(0)//Repeticiones de los abdominales
    var speedTime by mutableDoubleStateOf(0.0)//Tiempo de velocidad
    var runTime by mutableIntStateOf(0)//Tiempo en la carrera

    /*
    Utilizo derivedStateOf() para mejorar el rendimiento de la aplicación.
    De esta manera no consume tantos recursos al no tener que recomponer la interfaz de usuario con cada cambio
     */

    //Rango de cada prueba, derivan de ageGroup y isMale
    val pushUpRange by derivedStateOf {
        ScoreTables.getPushUpRange(ageGroup, isMale)
    }
    val absRange by derivedStateOf {
        ScoreTables.getAbsRange(ageGroup, isMale)
    }
    val speedRange by derivedStateOf {
        ScoreTables.getSpeedRange(ageGroup, isMale)
    }
    val runRange by derivedStateOf {
        ScoreTables.getRunRange(ageGroup, isMale)
    }

    //Puntos en cada prueba, derivan de ageGroup, isMale y de las repeticiones de cada prueba (pushCount, absCount...)
    val pushPoint by derivedStateOf {
        ScoreTables.getPushUpScore(ageGroup, isMale, pushCount)
    }
    val absPoint by derivedStateOf {
        ScoreTables.getAbsScore(ageGroup, isMale, absCount)
    }
    val runPoint by derivedStateOf {
        ScoreTables.getRunScore(ageGroup, isMale, runTime)
    }
    val speedPoint by derivedStateOf {
        ScoreTables.getSpeedScore(ageGroup, isMale, speedTime)
    }


    private val ageGroup by derivedStateOf {
        when (ageInput) {
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
    }

    //Calculo para la nota final
    val result by derivedStateOf {
        var totalScore=if (isAplChecked) {
            ((speedPoint.toDouble() + runPoint.toDouble()) / 2)
        } else {
            (((pushPoint.toDouble() + absPoint.toDouble()) +
                    speedPoint.toDouble() + runPoint.toDouble()) / 4)
        }
        "%.2f".format(totalScore / 10.0).replace(',', '.').toDouble()
    }

    init {
        speedTime = Double.MAX_VALUE
    }

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
        closeAgeDialog()
    }

    //Funcion para actualizar el valor del Slider de Edad
    fun updateAgeTemplete(value: Float) {
        _ageDialog = value.toInt()
    }

}