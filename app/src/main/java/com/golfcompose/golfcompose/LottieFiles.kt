package com.golfcompose.golfcompose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieClipSpec
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieAnimatable
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun Lottie(rawResId: Int, modifier: Modifier) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(rawResId)
    )
    val lottieAnimatable = rememberLottieAnimatable()

    LaunchedEffect(composition) {
        lottieAnimatable.animate(
            composition = composition,
            clipSpec = LottieClipSpec.Frame(0, 1200),
            initialProgress = 0f,
            iterations = LottieConstants.IterateForever
        )
    }

    Box(
        modifier = modifier
    ) {
        LottieAnimation(
            composition = composition,
            progress = lottieAnimatable.progress,
            contentScale = ContentScale.Inside,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LottieFilesPreview() {
    Lottie(R.raw.spaceship, modifier = Modifier.size(500.dp, 500.dp))
}
