package com.divergentapp.qrtoolkit.features.scanner.ui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.divergentapp.qrtoolkit.features.scanner.components.ScannerFrameDefaults

@Composable
fun ScannerAnimatedLaser(
    modifier: Modifier = Modifier
) {

    val transition = rememberInfiniteTransition(label = "scanner_laser")
    val firstColor = MaterialTheme.colorScheme.onPrimaryFixed
    val secondColor = MaterialTheme.colorScheme.onPrimaryFixedVariant
    val thirdColor = MaterialTheme.colorScheme.primaryFixedDim

    val progress = transition.animateFloat(

        initialValue = 0f,

        targetValue = 1f,

        animationSpec = infiniteRepeatable(

            animation = tween(
                durationMillis = 2200,
                easing = LinearEasing
            ),

            repeatMode = RepeatMode.Restart

        ),

        label = "laser_progress"

    )

    Canvas(
        modifier = modifier.fillMaxSize()
    ) {

        val frame = ScannerFrameDefaults.create(
            canvasSize = size,
            density = this
        )

        val y = frame.top + frame.height * progress.value

        drawLine(

            brush = Brush.horizontalGradient(

                listOf(
                    Color.Transparent,
                    firstColor,
                    Color.White,
                    secondColor,
                    Color.Transparent
                )

            ),

            start = Offset(
                frame.left,
                y
            ),

            end = Offset(
                frame.right,
                y
            ),

            strokeWidth = 3.dp.toPx()

        )

        drawLine(

            color = thirdColor.copy(0.66f),

            start = Offset(
                frame.left,
                y
            ),

            end = Offset(
                frame.right,
                y
            ),

            strokeWidth = 12.dp.toPx()

        )

    }

}