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
public val qr_code: ImageVector
    get() {
        if (_qr_code != null) {
            return _qr_code!!
        }
        _qr_code =
            ImageVector.Builder(
                name = "qr_code",
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
                        moveTo(3f, 11f)
                        verticalLineTo(3f)
                        horizontalLineToRelative(8f)
                        verticalLineToRelative(8f)
                        horizontalLineTo(3f)
                        close()
                        moveTo(5f, 9f)
                        horizontalLineTo(9f)
                        verticalLineTo(5f)
                        horizontalLineTo(5f)
                        verticalLineTo(9f)
                        close()
                        moveTo(3f, 21f)
                        verticalLineTo(13f)
                        horizontalLineToRelative(8f)
                        verticalLineToRelative(8f)
                        horizontalLineTo(3f)
                        close()
                        moveTo(5f, 19f)
                        horizontalLineTo(9f)
                        verticalLineTo(15f)
                        horizontalLineTo(5f)
                        verticalLineToRelative(4f)
                        close()
                        moveToRelative(8f, -8f)
                        verticalLineTo(3f)
                        horizontalLineToRelative(8f)
                        verticalLineToRelative(8f)
                        horizontalLineTo(13f)
                        close()
                        moveTo(15f, 9f)
                        horizontalLineToRelative(4f)
                        verticalLineTo(5f)
                        horizontalLineTo(15f)
                        verticalLineTo(9f)
                        close()
                        moveToRelative(4f, 12f)
                        verticalLineTo(19f)
                        horizontalLineToRelative(2f)
                        verticalLineToRelative(2f)
                        horizontalLineTo(19f)
                        close()
                        moveTo(13f, 15f)
                        verticalLineTo(13f)
                        horizontalLineToRelative(2f)
                        verticalLineToRelative(2f)
                        horizontalLineTo(13f)
                        close()
                        moveToRelative(2f, 2f)
                        verticalLineTo(15f)
                        horizontalLineToRelative(2f)
                        verticalLineToRelative(2f)
                        horizontalLineTo(15f)
                        close()
                        moveToRelative(-2f, 2f)
                        verticalLineTo(17f)
                        horizontalLineToRelative(2f)
                        verticalLineToRelative(2f)
                        horizontalLineTo(13f)
                        close()
                        moveToRelative(2f, 2f)
                        verticalLineTo(19f)
                        horizontalLineToRelative(2f)
                        verticalLineToRelative(2f)
                        horizontalLineTo(15f)
                        close()
                        moveToRelative(2f, -2f)
                        verticalLineTo(17f)
                        horizontalLineToRelative(2f)
                        verticalLineToRelative(2f)
                        horizontalLineTo(17f)
                        close()
                        moveToRelative(0f, -4f)
                        verticalLineTo(13f)
                        horizontalLineToRelative(2f)
                        verticalLineToRelative(2f)
                        horizontalLineTo(17f)
                        close()
                        moveToRelative(2f, 2f)
                        verticalLineTo(15f)
                        horizontalLineToRelative(2f)
                        verticalLineToRelative(2f)
                        horizontalLineTo(19f)
                        close()
                    }
                }
                .build()
        return _qr_code!!
    }

private var _qr_code: ImageVector? = null