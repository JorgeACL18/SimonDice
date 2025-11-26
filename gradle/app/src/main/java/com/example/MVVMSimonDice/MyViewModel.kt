// MyViewModel.kt (Actualizado)
package com.example.MVVMSimonDice

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class MyViewModel : ViewModel() {
    val TAG_LOG = "miDebug"

    val _estadoActual = MutableStateFlow(Estados.INICIO)
    val estadoActual: StateFlow<Estados> = _estadoActual

    val _secuencia = mutableStateListOf<Int>()
    val _nivelActual = MutableStateFlow(1)
    val nivelActual: StateFlow<Int> = _nivelActual

    val _indiceJugador = MutableStateFlow(0)
    val _tiempoRestante = MutableStateFlow(0)
    var _tiempoTotalPartida = 0

    val _botonIluminado = MutableStateFlow<Int?>(null) // null significa ninguno iluminado
    val botonIluminado: StateFlow<Int?> = _botonIluminado

    var _temporizadorActivo = false
    var _nivelMaximo = 10

    fun iniciarJuego() {
        Log.d(TAG_LOG, "iniciarJuego() - Estado actual: ${_estadoActual.value.name}")
        _estadoActual.value = Estados.GENERANDO
        Log.d(TAG_LOG, "iniciarJuego() - Estado cambiado a: ${_estadoActual.value.name}")
        _nivelActual.value = 1
        _secuencia.clear()
        _indiceJugador.value = 0
        _tiempoRestante.value = calcularTiempoPorNivel(1)
        _estadoActual.value = Estados.MOSTRANDO_SEC // Cambiar a MOSTRANDO_SEC inmediatamente
        Log.d(TAG_LOG, "iniciarJuego() - Estado cambiado a: ${_estadoActual.value.name}")
        iniciarNivel()
    }

    private fun iniciarNivel() {
        Log.d(TAG_LOG, "iniciarNivel() - Estado actual: ${_estadoActual.value.name}")
        Log.d(TAG_LOG, "Iniciando nivel ${_nivelActual.value}")
        _secuencia.add(Random.nextInt(4))
        Log.d(TAG_LOG, "Secuencia generada: ${_secuencia.joinToString(", ")}")
        _indiceJugador.value = 0
        _tiempoRestante.value = calcularTiempoPorNivel(_nivelActual.value)
        reproducirSecuencia()
    }

    private fun reproducirSecuencia() {
        viewModelScope.launch {
            Log.d(TAG_LOG, "reproducirSecuencia() - Estado actual: ${_estadoActual.value.name}")
            Log.d(TAG_LOG, "Reproduciendo secuencia: ${_secuencia.joinToString(", ")}")
            for (i in _secuencia.indices) {
                val colorIndex = _secuencia[i]
                Log.d(TAG_LOG, "Iluminando botón en índice: $colorIndex")
                _botonIluminado.value = colorIndex
                reproducirTono(Colores.values()[colorIndex])
                delay(800L)
                _botonIluminado.value = null
                delay(200L)
            }
            Log.d(TAG_LOG, "Fin de la reproducción de la secuencia.")
            // Cambiar a ADIVINANDO DESPUÉS de reproducir
            _estadoActual.value = Estados.ADIVINANDO
            Log.d(TAG_LOG, "reproducirSecuencia() - Estado cambiado a: ${_estadoActual.value.name}")
            iniciarTemporizador()
        }
    }

    private fun reproducirTono(color: Colores) {
        val toneGen = android.media.ToneGenerator(android.media.AudioManager.STREAM_MUSIC, 100)
        toneGen.startTone(color.tono, 200)
        Thread {
            Thread.sleep(250)
            toneGen.release()
        }.start()
    }

    private fun iniciarTemporizador() {
        if (_temporizadorActivo) return
        _temporizadorActivo = true
        viewModelScope.launch {
            Log.d(TAG_LOG, "iniciarTemporizador() - Iniciando temporizador con ${_tiempoRestante.value}s")
            while (_tiempoRestante.value > 0 && _estadoActual.value == Estados.ADIVINANDO) {
                delay(1000L)
                _tiempoRestante.value--
                Log.d(TAG_LOG, "Temporizador: ${_tiempoRestante.value}s restantes, Estado: ${_estadoActual.value.name}")
            }
            if (_tiempoRestante.value <= 0 && _estadoActual.value == Estados.ADIVINANDO) {
                Log.d(TAG_LOG, "Tiempo agotado!")
                perderJuego()
            }
            _temporizadorActivo = false
        }
    }

    fun procesarEntradaJugador(colorIndex: Int) {
        Log.d(TAG_LOG, "procesarEntradaJugador() - Estado actual: ${_estadoActual.value.name}, Color pulsado: $colorIndex")
        if (_estadoActual.value != Estados.ADIVINANDO) {
            Log.d(TAG_LOG, "Entrada ignorada, estado no es ADIVINANDO")
            return
        }

        if (colorIndex == _secuencia[_indiceJugador.value]) {
            Log.d(TAG_LOG, "Correcto! Botón ${colorIndex}")
            _indiceJugador.value++
            if (_indiceJugador.value == _secuencia.size) {
                Log.d(TAG_LOG, "Nivel completado!")
                if (_nivelActual.value >= _nivelMaximo) {
                    ganarJuego()
                } else {
                    _nivelActual.value++
                    // Cambiar estado antes de iniciar nuevo nivel
                    _estadoActual.value = Estados.MOSTRANDO_SEC
                    Log.d(TAG_LOG, "procesarEntradaJugador() - Estado cambiado a: ${_estadoActual.value.name} para nuevo nivel")
                    iniciarNivel()
                }
            }
        } else {
            Log.d(TAG_LOG, "Incorrecto! Se esperaba ${_secuencia[_indiceJugador.value]}, se recibió $colorIndex")
            perderJuego()
        }
    }

    private fun perderJuego() {
        _estadoActual.value = Estados.JUEGO_PERDIDO
        Log.d(TAG_LOG, "perderJuego() - Estado cambiado a: ${_estadoActual.value.name}")
    }

    private fun ganarJuego() {
        _estadoActual.value = Estados.JUEGO_GANADO
        Log.d(TAG_LOG, "ganarJuego() - Estado cambiado a: ${_estadoActual.value.name}")
    }

    fun reiniciarJuego() {
        Log.d(TAG_LOG, "reiniciarJuego() - Estado actual: ${_estadoActual.value.name}")
        _estadoActual.value = Estados.INICIO
        Log.d(TAG_LOG, "reiniciarJuego() - Estado cambiado a: ${_estadoActual.value.name}")
        _nivelActual.value = 1
        _secuencia.clear()
        _indiceJugador.value = 0
        _tiempoRestante.value = 0
        _botonIluminado.value = null
    }

    private fun calcularTiempoPorNivel(nivel: Int): Int {
        return maxOf(7, 15 - (nivel * 2))
    }
}