package com.divergentapp.qrtoolkit.features.scanner.components

import androidx.compose.ui.geometry.Rect

data class ScannerFrame(

    val rect: Rect,

    val cornerRadius: Float

) {

    val left: Float
        get() = rect.left

    val top: Float
        get() = rect.top

    val right: Float
        get() = rect.right

    val bottom: Float
        get() = rect.bottom

    val width: Float
        get() = rect.width

    val height: Float
        get() = rect.height

    val centerX: Float
        get() = rect.center.x

    val centerY: Float
        get() = rect.center.y

}