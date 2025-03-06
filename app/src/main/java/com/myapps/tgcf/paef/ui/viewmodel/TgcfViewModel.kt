package com.myapps.tgcf.paef.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.myapps.tgcf.paef.data.ScoreTables
import javax.inject.Inject

class TgcfViewModel @Inject constructor() : ViewModel() {

    var ageInput by mutableStateOf(0)
    var pushCount by mutableStateOf(0)
    var absCount by mutableStateOf(0)
    var speedTime by mutableStateOf(0.0)
    var runTime by mutableStateOf(0)

    private var ageGroup = 1
    private var isMale = true
    private var pushScore = 0
    private var absScore = 0
    private var speedScore = 0
    private var runScore = 0

    //Livedata para mostrar u ocultar las celdas en caso de que esté marcado APL
    private val _showApl = MutableLiveData<Boolean>()
    val showApl: LiveData<Boolean> = _showApl


    fun onShowApl() {
        _showApl.value = true
    }

    fun onCoverUpDelte() {
        _showApl.value = false
    }

    fun calculateFinalScore() {
        ageGroup = when (ageInput) {
            in 17..21 -> 1
            in 22..26 -> 2
            in 27..31 -> 3
            in 32..36 -> 4
            in 37..41 -> 5
            in 42..46 -> 6
            in 47..51 -> 7
            in 52..56 -> 8
            in 57..61 -> 9
            else -> 10
        }

        pushScore=ScoreTables.getPushScore(ageGroup, isMale, pushCount)
        absScore=ScoreTables.getAbsScore(ageGroup, isMale, absCount)
    }


}