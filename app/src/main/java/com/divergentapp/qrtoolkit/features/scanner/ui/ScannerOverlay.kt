package com.divergentapp.qrtoolkit.features.scanner.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.dp
import com.divergentapp.qrtoolkit.features.scanner.components.ScannerFrameDefaults

@Composable
fun ScannerOverlay(
    modifier: Modifier = Modifier
) {
    val color = MaterialTheme.colorScheme.surface

    Canvas(
        modifier = modifier.fillMaxSize()
    ) {

        val frame = ScannerFrameDefaults.create(
            canvasSize = size,
            density = this
        )

        val overlayColor = Color.Black.copy(alpha = 0.60f)

        // Top
        drawRect(
            color = overlayColor,
            topLeft = Offset.Zero,
            size = Size(
                width = size.width,
                height = frame.top
            )
        )

        // Bottom
        drawRect(
            color = overlayColor,
            topLeft = Offset(
                0f,
                frame.bottom
            ),
            size = Size(
                width = size.width,
                height = size.height - frame.bottom
            )
        )

        // Left
        drawRect(
            color = overlayColor,
            topLeft = Offset(
                0f,
                frame.top
            ),
            size = Size(
                width = frame.left,
                height = frame.height
            )
        )

        // Right
        drawRect(
            color = overlayColor,
            topLeft = Offset(
                frame.right,
                frame.top
            ),
            size = Size(
                width = size.width - frame.right,
                height = frame.height
            )
        )

        val cornerLength = 42.dp.toPx()
        val strokeWidth = 5.dp.toPx()

        drawCorner(
            origin = Offset(frame.left, frame.top),
            horizontalPositive = true,
            verticalPositive = true,
            length = cornerLength,
            strokeWidth = strokeWidth,
            color = color
        )

        drawCorner(
            origin = Offset(frame.right, frame.top),
            horizontalPositive = false,
            verticalPositive = true,
            length = cornerLength,
            strokeWidth = strokeWidth,
            color = color
        )

        drawCorner(
            origin = Offset(frame.left, frame.bottom),
            horizontalPositive = true,
            verticalPositive = false,
            length = cornerLength,
            strokeWidth = strokeWidth,
            color = color
        )

        drawCorner(
            origin = Offset(frame.right, frame.bottom),
            horizontalPositive = false,
            verticalPositive = false,
            length = cornerLength,
            strokeWidth = strokeWidth,
            color = color
        )
    }
}

private fun DrawScope.drawCorner(
    origin: Offset,
    horizontalPositive: Boolean,
    verticalPositive: Boolean,
    length: Float,
    strokeWidth: Float,
    color : Color
) {

    val horizontalEnd = Offset(
        x = if (horizontalPositive) origin.x + length else origin.x - length,
        y = origin.y
    )

    val verticalEnd = Offset(
        x = origin.x,
        y = if (verticalPositive) origin.y + length else origin.y - length
    )

    drawLine(
        color = color,
        start = origin,
        end = horizontalEnd,
        strokeWidth = strokeWidth,
        cap = StrokeCap.Round
    )

    drawLine(
        color = color,
        start = origin,
        end = verticalEnd,
        strokeWidth = strokeWidth,
        cap = StrokeCap.Round
    )

}