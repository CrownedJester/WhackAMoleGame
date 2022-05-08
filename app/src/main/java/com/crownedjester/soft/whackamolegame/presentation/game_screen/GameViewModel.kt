package com.crownedjester.soft.whackamolegame.presentation.game_screen

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.random.Random

class GameViewModel : ViewModel() {

    private val _currentRevealedMole = MutableStateFlow(-1)
    val currentRevealedMole: StateFlow<Int> get() = _currentRevealedMole

    private val _tappedMoleCount = mutableStateOf(0)
    val tappedMoleCount: State<Int> get() = _tappedMoleCount

    private val _timerStateFlow = MutableStateFlow(0)
    val timerStateFlow: StateFlow<Int> get() = _timerStateFlow

    init {
        runSpawner()
        runTimer()
    }

    fun onMoleTap() {
        viewModelScope.launch {
            _tappedMoleCount.value++
            _currentRevealedMole.emit(-1)
        }
    }

    private fun runSpawner() {
        viewModelScope.launch {
            initSpawner()
                .onCompletion { _currentRevealedMole.value = -1 }
                .collect {

                }
        }
    }

    private fun initSpawner(time: Int = 30, delay: Long = 1000) =
        (time * (1000 / delay.toInt()) downTo 0).asFlow()
            .onEach {
                val number = Random.nextInt(0, 9)

                _currentRevealedMole.emit(number)

                delay(delay)

                Log.i("ViewModel", _currentRevealedMole.value.toString())
            }.conflate()

    private fun runTimer() {
        viewModelScope.launch {
            initTimer()
                .collect {
                    _timerStateFlow.emit(it)
                }
        }
    }

    private fun initTimer(time: Int = 30) =
        (time downTo 0 step 1).asFlow()
            .onStart {
                _timerStateFlow.emit(time)
            }
            .onEach {
                delay(1000)
            }
            .conflate()
}
