package com.golfcompose.golfcompose


import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun GradeImageId(TotalAttendance : Int) {

    val imageModifier = Modifier.size(40.dp, 40.dp)

    DividingGrades(TotalAttendance, imageModifier)

}

@Composable
fun DividingGrades(TotalAttendance: Int, imageModifier: Modifier) {
    when (TotalAttendance) {
        1 -> Lottie(rawResId = R.raw.dog, modifier = imageModifier)
        2 -> Lottie(rawResId = R.raw.heart, modifier = imageModifier)
        else -> Lottie(rawResId = R.raw.fire, modifier = imageModifier)
    }
}

