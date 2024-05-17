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
        2 -> Lottie(rawResId = R.raw.carousel, modifier = imageModifier)
        3 -> Lottie(rawResId = R.raw.chicken, modifier = imageModifier)
        4 -> Lottie(rawResId = R.raw.dark_house, modifier = imageModifier)
        5 -> Lottie(rawResId = R.raw.dinosaur, modifier = imageModifier)
        6 -> Lottie(rawResId = R.raw.dog, modifier = imageModifier)
        7 -> Lottie(rawResId = R.raw.fire, modifier = imageModifier)
        8 -> Lottie(rawResId = R.raw.flight, modifier = imageModifier)
        9 -> Lottie(rawResId = R.raw.ghost, modifier = imageModifier)
        10 -> Lottie(rawResId = R.raw.heart, modifier = imageModifier)
        11 -> Lottie(rawResId = R.raw.hedgehog, modifier = imageModifier)
        12 -> Lottie(rawResId = R.raw.house, modifier = imageModifier)
        13 -> Lottie(rawResId = R.raw.kiki, modifier = imageModifier)
        14 -> Lottie(rawResId = R.raw.llama, modifier = imageModifier)
        15 -> Lottie(rawResId = R.raw.dragon, modifier = imageModifier)
        16 -> Lottie(rawResId = R.raw.potato, modifier = imageModifier)
        17 -> Lottie(rawResId = R.raw.rabbit, modifier = imageModifier)
        18 -> Lottie(rawResId = R.raw.rainbow, modifier = imageModifier)
        19 -> Lottie(rawResId = R.raw.rocket, modifier = imageModifier)
        20 -> Lottie(rawResId = R.raw.shark, modifier = imageModifier)
        21 -> Lottie(rawResId = R.raw.sloth, modifier = imageModifier)
        22 -> Lottie(rawResId = R.raw.snowman, modifier = imageModifier)
        23 -> Lottie(rawResId = R.raw.spaceship, modifier = imageModifier)
        24 -> Lottie(rawResId = R.raw.star, modifier = imageModifier)
        25 -> Lottie(rawResId = R.raw.tornado, modifier = imageModifier)
        26 -> Lottie(rawResId = R.raw.sushi, modifier = imageModifier)
        27 -> Lottie(rawResId = R.raw.taco, modifier = imageModifier)
        28 -> Lottie(rawResId = R.raw.tiger, modifier = imageModifier)
        29 -> Lottie(rawResId = R.raw.toaster, modifier = imageModifier)
        30 -> Lottie(rawResId = R.raw.sandwitch, modifier = imageModifier)
        31 -> Lottie(rawResId = R.raw.bomb, modifier = imageModifier)
        else -> Lottie(rawResId = R.raw.donut, modifier = imageModifier)

    }
}

