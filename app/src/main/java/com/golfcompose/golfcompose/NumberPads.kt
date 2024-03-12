
package com.golfcompose.golfcompose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NumberPads(number: String, onNumberAdded: (Int) -> Unit, onDeleted: () -> Unit, onChecked: () -> Unit) {
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
            CheckPad { onChecked() }
        }
    }
}

@Composable
fun NumberPad(padNumber: Int, onClick: (Int) -> Unit) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        onClick = { onClick(padNumber) }
    ) {
        Text("$padNumber", fontSize = 50.sp)
    }
}

@Composable
fun DeletePad(onClick: () -> Unit) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        onClick = { onClick() }) {
        Text("지우기", fontSize = 30.sp)
    }
}

@Composable
fun CheckPad(onClick: () -> Unit) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        onClick = { onClick() }) {
        Text("확인", fontSize = 30.sp)
    }
}