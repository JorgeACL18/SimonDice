package com.example.MVVMSimonDice

import android.media.ToneGenerator
import androidx.compose.ui.graphics.Color
/**
 * Clase para almacenar los datos del juego
 */

enum class Colores(val color: Color, val txt: String, val tono: Int) {
    CLASE_ROJO(color = Color.Red, txt = "", tono = ToneGenerator.TONE_DTMF_1),
    CLASE_VERDE(color = Color.Green, txt = "", tono = ToneGenerator.TONE_DTMF_2),
    CLASE_AZUL(color = Color.Blue, txt = "", tono = ToneGenerator.TONE_DTMF_3),
    CLASE_AMARILLO(color = Color.Yellow, txt = "", tono = ToneGenerator.TONE_DTMF_4),
    CLASE_START(color = Color.Black, txt = "Start", tono = ToneGenerator.TONE_PROP_BEEP2)
}

/**
 * Estados del juego
 */
enum class Estados(val start_activo: Boolean, val boton_activo: Boolean) {
    INICIO(start_activo = true, boton_activo = false), // Esperando para iniciar
    GENERANDO(start_activo = false, boton_activo = false), //Generando secuencia
    MOSTRANDO_SEC( start_activo = false, boton_activo = false), // Mostrando la secuencia
    ADIVINANDO(start_activo = false, boton_activo = true), // Jugador debe adivinar
    JUEGO_PERDIDO(start_activo = true, boton_activo = false), // Mostrar mensaje de partida terminada/perdida
    JUEGO_GANADO(start_activo = false, boton_activo = false) // Mostrar mensaje de ganador
}