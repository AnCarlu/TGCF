package com.myapps.tgcf

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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RadialGradientShader
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.core.view.WindowCompat
import com.myapps.tgcf.paef.ui.view.Screen
import com.myapps.tgcf.paef.ui.viewmodel.TgcfViewModel
import com.myapps.tgcf.ui.theme.TgcfTheme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val gradienteRadialGrande = remember {
                object : ShaderBrush() {
                    override fun createShader(size: Size): Shader {
                        val dimensionMayor = maxOf(size.height, size.width)
                        return RadialGradientShader(
                            colors = listOf(Color(0xFF2be4dc), Color(0xFF243484)),
                            center = size.center,
                            radius = dimensionMayor / 2f,
                            colorStops = listOf(0f, 0.95f)
                        )
                    }
                }
            }
            TgcfTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val taskViewModel: TgcfViewModel by viewModels()
                    Screen(
                        Modifier
                            .padding(innerPadding)
                            .background(gradienteRadialGrande),
                        taskViewModel
                    )
                }
            }
        }
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT
    }
}




