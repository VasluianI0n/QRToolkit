package com.divergentapp.qrtoolkit.features.scanner.components

import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp

object ScannerFrameDefaults {

    const val FrameWidthPercent = 0.72f

    fun create(

        canvasSize: Size,

        density: Density

    ): ScannerFrame {

        with(density) {

            val frameSize = canvasSize.width * FrameWidthPercent

            val left = (canvasSize.width - frameSize) / 2f

            val top = (canvasSize.height - frameSize) / 2f

            return ScannerFrame(

                rect = Rect(

                    left,

                    top,

                    left + frameSize,

                    top + frameSize

                ),

                cornerRadius = 32.dp.toPx()

            )

        }

    }

}