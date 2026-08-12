package com.divergentapp.qrtoolkit.core.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

@Suppress("CheckReturnValue")
public val flash_off: ImageVector
    get() {
        if (_flash_off != null) {
            return _flash_off!!
        }
        _flash_off =
            ImageVector.Builder(
                name = "flash_off",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 24f,
                viewportHeight = 24f,
            )
                .apply {
                    path(
                        fill = SolidColor(Color.Black),
                        fillAlpha = 1f,
                        stroke = null,
                        strokeAlpha = 1f,
                        strokeLineWidth = 1f,
                        strokeLineCap = StrokeCap.Butt,
                        strokeLineJoin = StrokeJoin.Bevel,
                        strokeLineMiter = 1f,
                        pathFillType = PathFillType.Companion.NonZero,
                    ) {
                        moveTo(7f, 2f)
                        horizontalLineTo(17f)
                        lineTo(15f, 9f)
                        horizontalLineToRelative(4f)
                        lineToRelative(-2.93f, 4.22f)
                        lineTo(14.65f, 11.8f)
                        lineTo(15.2f, 11f)
                        horizontalLineTo(13.85f)
                        lineTo(12.68f, 9.82f)
                        lineTo(14.35f, 4f)
                        horizontalLineTo(9f)
                        verticalLineTo(6.15f)
                        lineToRelative(-2f, -2f)
                        verticalLineTo(2f)
                        close()
                        moveToRelative(3f, 20f)
                        verticalLineTo(14f)
                        horizontalLineTo(7f)
                        verticalLineTo(9.85f)
                        lineTo(1.38f, 4.22f)
                        lineTo(2.8f, 2.8f)
                        lineTo(21.2f, 21.2f)
                        lineToRelative(-1.43f, 1.43f)
                        lineTo(13.75f, 16.6f)
                        lineTo(10f, 22f)
                        close()
                        moveTo(11.83f, 8.98f)
                        close()
                    }
                }
                .build()
        return _flash_off!!
    }

private var _flash_off: ImageVector? = null