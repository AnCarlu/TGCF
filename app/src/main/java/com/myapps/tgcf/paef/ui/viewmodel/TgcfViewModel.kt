package com.myapps.tgcf.paef.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class TgcfViewModel @Inject constructor() : ViewModel() {

    //Livedata para mostrar u ocultar las celdas en caso de que esté marcado APL
    private val _showApl = MutableLiveData<Boolean>()
    val showApl: LiveData<Boolean> = _showApl


    fun onShowApl(){
        _showApl.value=true
    }

    fun onCoverUpDelte(){
        _showApl.value=false
    }

}