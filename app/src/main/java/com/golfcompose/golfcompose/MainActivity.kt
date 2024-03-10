package com.golfcompose.golfcompose

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.golfcompose.golfcompose.ui.theme.GolfComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GolfComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}
@Composable
fun MainScreen() {
    var number by remember { mutableStateOf("010-") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = number,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 30.sp
        )
        NumberPads(number,
            onNumberAdded = { addedNumber ->
            number += addedNumber.toString()
        },
            onDeleted = { ->
                if (number.isNotEmpty()) {
                    number = number.substring(0, number.length - 1)
                }
            },
            onChecked = { ->
                number = ""
            }
        )
    }
}

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
        onClick = {onClick()}) {
        Text("확인", fontSize = 30.sp)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NumberPadsPreview() {
    GolfComposeTheme {
        MainScreen()
    }
}