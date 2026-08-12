package com.divergentapp.qrtoolkit.core.qr

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel

data class QRCodeOptions(

    val size: Int = 1024,

    val foregroundColor: Int = Color(0xFF235347).toArgb(),

    val backgroundColor: Int = Color.White.toArgb(),

    val margin: Int = 1,

    val errorCorrection: ErrorCorrectionLevel =
        ErrorCorrectionLevel.M,
)