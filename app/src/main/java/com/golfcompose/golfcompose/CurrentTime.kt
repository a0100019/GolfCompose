package com.golfcompose.golfcompose

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun CurrentTimeText(
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TextStyle(
        fontSize = 30.sp,
        fontWeight = FontWeight.Normal
    )
) {
    var currentTime by remember { mutableStateOf(getCurrentTime()) }

    LaunchedEffect(Unit) {
        while (true) {
            currentTime = getCurrentTime()
            delay(1000) // 1초마다 업데이트
        }
    }

    Text(
        text = currentTime,
        style = textStyle,
        modifier = modifier.padding(8.dp)
    )
}

private fun getCurrentTime(): String {
    val currentTime = Calendar.getInstance().time
    val dateFormat = SimpleDateFormat("HH시 mm분", Locale.getDefault())
    return dateFormat.format(currentTime)
}
