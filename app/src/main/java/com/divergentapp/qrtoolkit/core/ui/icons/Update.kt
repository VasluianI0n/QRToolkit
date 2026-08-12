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
public val update: ImageVector
    get() {
        if (_update != null) {
            return _update!!
        }
        _update =
            ImageVector.Builder(
                name = "update",
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
                        moveTo(12f, 21f)
                        quadTo(10.13f, 21f, 8.49f, 20.29f)
                        reflectiveQuadTo(5.64f, 18.36f)
                        reflectiveQuadTo(3.71f, 15.51f)
                        reflectiveQuadTo(3f, 12f)
                        reflectiveQuadTo(3.71f, 8.49f)
                        reflectiveQuadTo(5.64f, 5.64f)
                        quadTo(6.85f, 4.42f, 8.49f, 3.71f)
                        reflectiveQuadTo(12f, 3f)
                        quadToRelative(2.05f, 0f, 3.89f, 0.88f)
                        reflectiveQuadTo(19f, 6.35f)
                        verticalLineTo(4f)
                        horizontalLineToRelative(2f)
                        verticalLineToRelative(6f)
                        horizontalLineTo(15f)
                        verticalLineTo(8f)
                        horizontalLineToRelative(2.75f)
                        quadTo(16.73f, 6.6f, 15.23f, 5.8f)
                        reflectiveQuadTo(12f, 5f)
                        quadTo(9.08f, 5f, 7.04f, 7.04f)
                        reflectiveQuadTo(5f, 12f)
                        reflectiveQuadToRelative(2.04f, 4.96f)
                        reflectiveQuadTo(12f, 19f)
                        quadToRelative(2.63f, 0f, 4.59f, -1.7f)
                        reflectiveQuadTo(18.9f, 13f)
                        horizontalLineToRelative(2.05f)
                        quadToRelative(-0.38f, 3.43f, -2.94f, 5.71f)
                        reflectiveQuadTo(12f, 21f)
                        close()
                        moveToRelative(2.8f, -4.8f)
                        lineTo(11f, 12.4f)
                        verticalLineTo(7f)
                        horizontalLineToRelative(2f)
                        verticalLineToRelative(4.6f)
                        lineToRelative(3.2f, 3.2f)
                        lineToRelative(-1.4f, 1.4f)
                        close()
                    }
                }
                .build()
        return _update!!
    }

private var _update: ImageVector? = null