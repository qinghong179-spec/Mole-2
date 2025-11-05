package com.example.mole

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.IntSize
import kotlin.random.Random

class MoleViewModel : ViewModel() {

    var counter by mutableLongStateOf(0)
        private set

    var stay by mutableLongStateOf(0)
        private set

    var maxX by mutableStateOf(0)
        private set

    var maxY by mutableStateOf(0)
        private set

    var offsetX by mutableStateOf(0)
        private set
    var offsetY by mutableStateOf(0)
        private set

    init {
        startCounting()
    }

    private fun startCounting() {
        viewModelScope.launch {

            while (stay < 60) {
                delay(1000L)
                stay++
                moveMole()
            }
        }
    }
    fun incrementCounter() {
        if (stay < 60) {
            counter++
        }
    }
    fun moveMole() {
        if (maxX >= 0 && maxY >= 0) {
            offsetX = (0..maxX).random()
            offsetY = (0..maxY).random()
        }
    }

    fun getArea(gameSize: IntSize, moleSize: Int) {
        maxX = gameSize.width - moleSize
        maxY = gameSize.height - moleSize

        if (offsetX == 0 && offsetY == 0) {
            moveMole()
        }
    }
}