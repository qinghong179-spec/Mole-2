package com.example.mole

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class OwlViewModel : ViewModel() {
    // 貓頭鷹的座標 (Int 型別)
    var owlOffsetX by mutableIntStateOf(100)
        private set
    var owlOffsetY by mutableIntStateOf(400)
        private set

    // 處理拖曳事件：更新座標
    fun owlDrag(dragAmountX: Int, dragAmountY: Int) {
        owlOffsetX += dragAmountX
        owlOffsetY += dragAmountY
    }
}