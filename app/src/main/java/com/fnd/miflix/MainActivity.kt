package com.fnd.miflix

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import com.fnd.miflix.ui.theme.MiFlixTheme
import com.fnd.miflix.views.LoginScreen
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MiFlixTheme {
                setBarColor(color = MaterialTheme.colorScheme.background )
                LoginScreen()
            }
        }
    }

    @Composable
    private fun setBarColor(color: Color){
        val systemUiController = rememberSystemUiController()
        SideEffect {
            systemUiController.setStatusBarColor(
                color= color
            )
        }

    }
}
