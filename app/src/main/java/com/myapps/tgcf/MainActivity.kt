package com.myapps.tgcf

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.myapps.tgcf.paef.ui.view.Screen
import com.myapps.tgcf.ui.theme.TgcfTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TgcfTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Screen()
                }
            }
        }
    }
}




