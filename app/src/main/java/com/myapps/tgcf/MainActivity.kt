package com.myapps.tgcf

import android.app.Activity
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.myapps.tgcf.paef.ui.view.Screen
import com.myapps.tgcf.paef.ui.viewmodel.TgcfViewModel
import com.myapps.tgcf.ui.theme.TgcfTheme


val backgroundColor = Color(0xFFFFFFFF)

class MainActivity : ComponentActivity() {

    private val taskViewModel: TgcfViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation=ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT
        enableEdgeToEdge()

        setContent {
            TgcfTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Screen(Modifier.padding(innerPadding).background(color = backgroundColor), taskViewModel)
                    //Screen()
                }
            }
        }
    }
}




