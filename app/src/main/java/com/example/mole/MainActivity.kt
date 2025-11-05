package com.example.mole

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mole.ui.theme.MoleTheme
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MoleTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MoleScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun MoleScreen(
    modifier: Modifier = Modifier,
    moleViewModel: MoleViewModel = viewModel(),
    owlViewModel: OwlViewModel = viewModel()
) {
    // DP-to-pixel 轉換
    val density = LocalDensity.current
    val moleSizeDp = 150.dp
    val moleSizePx = with(density) { moleSizeDp.roundToPx() }

    // 從 ViewModel 讀取狀態並進行必要的 Long -> Int 轉換
    val moleOffsetX = moleViewModel.offsetX.toInt() // <-- 加上 .toInt()
    val moleOffsetY = moleViewModel.offsetY.toInt() // <-- 加上 .toInt()
    val owlOffsetX = owlViewModel.owlOffsetX
    val owlOffsetY = owlViewModel.owlOffsetY

    // 讓 Box 佔滿整個螢幕，但不設置 contentAlignment
    Box(
        modifier = modifier
            .fillMaxSize()
            .onSizeChanged { intSize -> // 用來獲取全螢幕尺寸px
                // 將整個螢幕尺寸傳遞給 ViewModel 計算移動範圍
                moleViewModel.getArea(intSize, moleSizePx)
            }
    ) {

        // Column 包含文字，並置於 Box 的中央
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 1. 分數/時間顯示
            Text(
                text = if (moleViewModel.stay >= 60) {
                    "打地鼠遊戲(洪詩晴)\n分數: ${moleViewModel.counter} \n時間: 60"
                } else {
                    "打地鼠遊戲(洪詩晴)\n分數: ${moleViewModel.counter} \n時間: ${moleViewModel.stay}"
                },
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 32.dp)
            )
        }

        // 2. 貓頭鷹 Image
        Image(
            painter = painterResource(id = R.drawable.owl),
            contentDescription = "貓頭鷹",
            modifier = Modifier
                .align(Alignment.TopStart)
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        owlViewModel.owlDrag(
                            dragAmountX = dragAmount.x.toInt(),
                            dragAmountY = dragAmount.y.toInt()
                        )
                    }
                }
                .offset {
                    IntOffset(owlOffsetX, owlOffsetY) // 應用 Int 偏移量
                }
                .size(moleSizeDp / 2)
        )

        // 3. 地鼠 Image
        Image(
            painter = painterResource(id = R.drawable.mole),
            contentDescription = "地鼠",
            modifier = Modifier
                .align(Alignment.TopStart)
                .offset { IntOffset(moleOffsetX, moleOffsetY) } // 應用 Int 偏移量
                .size(moleSizeDp)
                .clickable { moleViewModel.incrementCounter() }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MoleScreenPreview() {
    MoleTheme {
        MoleScreen()
    }
}