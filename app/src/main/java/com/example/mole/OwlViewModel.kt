package com.example.mole

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class OwlViewModel : ViewModel() {
    var owlOffsetX by mutableIntStateOf(100)
        private set
    var owlOffsetY by mutableIntStateOf(400)
        private set
    fun owlDrag(dragAmountX: Int, dragAmountY: Int) {
        owlOffsetX += dragAmountX
        owlOffsetY += dragAmountY
    }
}