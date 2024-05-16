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
        1 -> Lottie(rawResId = R.raw.bee, modifier = imageModifier)
        2 -> Lottie(rawResId = R.raw.heart, modifier = imageModifier)
        3 -> Lottie(rawResId = R.raw.fire, modifier = imageModifier)
        4 -> Lottie(rawResId = R.raw.fire, modifier = imageModifier)
        5 -> Lottie(rawResId = R.raw.fire, modifier = imageModifier)
        6 -> Lottie(rawResId = R.raw.fire, modifier = imageModifier)
        7 -> Lottie(rawResId = R.raw.fire, modifier = imageModifier)
        8 -> Lottie(rawResId = R.raw.fire, modifier = imageModifier)
        9 -> Lottie(rawResId = R.raw.fire, modifier = imageModifier)
        10 -> Lottie(rawResId = R.raw.fire, modifier = imageModifier)
        11 -> Lottie(rawResId = R.raw.fire, modifier = imageModifier)
        12 -> Lottie(rawResId = R.raw.fire, modifier = imageModifier)
        13 -> Lottie(rawResId = R.raw.fire, modifier = imageModifier)
        14 -> Lottie(rawResId = R.raw.fire, modifier = imageModifier)
        15 -> Lottie(rawResId = R.raw.fire, modifier = imageModifier)
        16 -> Lottie(rawResId = R.raw.fire, modifier = imageModifier)
        17 -> Lottie(rawResId = R.raw.fire, modifier = imageModifier)
        18 -> Lottie(rawResId = R.raw.fire, modifier = imageModifier)
        19 -> Lottie(rawResId = R.raw.fire, modifier = imageModifier)
        20 -> Lottie(rawResId = R.raw.fire, modifier = imageModifier)
        21 -> Lottie(rawResId = R.raw.fire, modifier = imageModifier)

    }
}

