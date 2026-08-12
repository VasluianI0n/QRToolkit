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
public val picture_as_pdf: ImageVector
    get() {
        if (_picture_as_pdf != null) {
            return _picture_as_pdf!!
        }
        _picture_as_pdf =
            ImageVector.Builder(
                name = "picture_as_pdf",
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
                        moveTo(9f, 12.5f)
                        horizontalLineToRelative(1f)
                        verticalLineToRelative(-2f)
                        horizontalLineToRelative(1f)
                        quadToRelative(0.43f, 0f, 0.71f, -0.29f)
                        reflectiveQuadTo(12f, 9.5f)
                        verticalLineToRelative(-1f)
                        quadTo(12f, 8.07f, 11.71f, 7.79f)
                        reflectiveQuadTo(11f, 7.5f)
                        horizontalLineTo(9f)
                        verticalLineToRelative(5f)
                        close()
                        moveToRelative(1f, -3f)
                        verticalLineToRelative(-1f)
                        horizontalLineToRelative(1f)
                        verticalLineToRelative(1f)
                        horizontalLineTo(10f)
                        close()
                        moveToRelative(3f, 3f)
                        horizontalLineToRelative(2f)
                        quadToRelative(0.43f, 0f, 0.71f, -0.29f)
                        quadTo(16f, 11.93f, 16f, 11.5f)
                        verticalLineToRelative(-3f)
                        quadTo(16f, 8.07f, 15.71f, 7.79f)
                        reflectiveQuadTo(15f, 7.5f)
                        horizontalLineTo(13f)
                        verticalLineToRelative(5f)
                        close()
                        moveToRelative(1f, -1f)
                        verticalLineToRelative(-3f)
                        horizontalLineToRelative(1f)
                        verticalLineToRelative(3f)
                        horizontalLineTo(14f)
                        close()
                        moveToRelative(3f, 1f)
                        horizontalLineToRelative(1f)
                        verticalLineToRelative(-2f)
                        horizontalLineToRelative(1f)
                        verticalLineToRelative(-1f)
                        horizontalLineTo(18f)
                        verticalLineToRelative(-1f)
                        horizontalLineToRelative(1f)
                        verticalLineToRelative(-1f)
                        horizontalLineTo(17f)
                        verticalLineToRelative(5f)
                        close()
                        moveTo(8f, 18f)
                        quadTo(7.18f, 18f, 6.59f, 17.41f)
                        reflectiveQuadTo(6f, 16f)
                        verticalLineTo(4f)
                        quadTo(6f, 3.17f, 6.59f, 2.59f)
                        reflectiveQuadTo(8f, 2f)
                        horizontalLineTo(20f)
                        quadToRelative(0.83f, 0f, 1.41f, 0.59f)
                        reflectiveQuadTo(22f, 4f)
                        verticalLineTo(16f)
                        quadToRelative(0f, 0.82f, -0.59f, 1.41f)
                        reflectiveQuadTo(20f, 18f)
                        horizontalLineTo(8f)
                        close()
                        moveTo(8f, 16f)
                        horizontalLineTo(20f)
                        verticalLineTo(4f)
                        horizontalLineTo(8f)
                        verticalLineTo(16f)
                        close()
                        moveTo(4f, 22f)
                        quadTo(3.18f, 22f, 2.59f, 21.41f)
                        reflectiveQuadTo(2f, 20f)
                        verticalLineTo(6f)
                        horizontalLineTo(4f)
                        verticalLineTo(20f)
                        horizontalLineTo(18f)
                        verticalLineToRelative(2f)
                        horizontalLineTo(4f)
                        close()
                        moveTo(8f, 4f)
                        verticalLineTo(16f)
                        verticalLineTo(4f)
                        close()
                    }
                }
                .build()
        return _picture_as_pdf!!
    }

private var _picture_as_pdf: ImageVector? = null