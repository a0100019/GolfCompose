package com.golfcompose.golfcompose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NumberPads(onNumberAdded: (Int) -> Unit, onDeleted: () -> Unit, onChecked: () -> Unit, numberDigit: Int) {


    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Bottom
    ) {
        Column(modifier = Modifier.weight(1f)) {
            NumberPad(padNumber = 1) { onNumberAdded(it) }
            NumberPad(padNumber = 4) { onNumberAdded(it) }
            NumberPad(padNumber = 7) { onNumberAdded(it) }
            DeletePad { onDeleted() }
        }
        Column(modifier = Modifier.weight(1f)) {
            NumberPad(padNumber = 2) { onNumberAdded(it) }
            NumberPad(padNumber = 5) { onNumberAdded(it) }
            NumberPad(padNumber = 8) { onNumberAdded(it) }
            NumberPad(padNumber = 0) { onNumberAdded(it) }
        }
        Column(modifier = Modifier.weight(1f)) {
            //it은 앞의 매개변수 즉 padNumber를 뜻함
            NumberPad(padNumber = 3) { onNumberAdded(it) }
            NumberPad(padNumber = 6) { onNumberAdded(it) }
            NumberPad(padNumber = 9) { onNumberAdded(it) }
            CheckPad(onClick = { onChecked() }, numberDigit = numberDigit)
        }
    }
}

@Composable
fun NumberPad(padNumber: Int, onClick: (Int) -> Unit) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(4.dp),
        onClick = { onClick(padNumber) },
        border = BorderStroke(1.dp, Color.Black),
        shape = RoundedCornerShape(4.dp) // 버튼의 둥근 정도를 조절하는 부분
// 테두리 추가
    ) {
        Text("$padNumber", fontSize = 50.sp)
    }
}

@Composable
fun DeletePad(onClick: () -> Unit) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(4.dp),
        onClick = { onClick() },
        border = BorderStroke(1.dp, Color.Black),
        shape = RoundedCornerShape(4.dp),
    ) {
        Text("지우기", fontSize = 30.sp)
    }
}

@Composable
fun CheckPad(onClick: () -> Unit, numberDigit: Int) {

    var clickEnabled by remember { mutableStateOf(false) } // 클릭 가능한 상태를 저장하는 State

    //if 문 간소화 ㄷㄷ
    //나중에 8로 변경해야됨!!!!!!!!!!!!!!!!!
    clickEnabled = numberDigit == 2

    Button(
        enabled = clickEnabled,
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(4.dp),
        border = BorderStroke(1.dp, Color.Black),
        shape = RoundedCornerShape(4.dp), // 버튼의 둥근 정도를 조절하는 부분
// 테두리 추가
        onClick = { onClick() }) {
        Text("확인", fontSize = 30.sp)
    }
}