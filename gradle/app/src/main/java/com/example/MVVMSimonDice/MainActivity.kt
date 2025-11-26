package com.example.MVVMSimonDice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.MVVMSimonDice.ui.theme.MVVMSimonDiceTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val miViewModel = MyViewModel()

        enableEdgeToEdge()
        setContent {
            MVVMSimonDiceTheme {
                IU(miViewModel)
            }
        }
    }
}