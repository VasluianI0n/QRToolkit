package com.divergentapp.qrtoolkit.core.qr

import android.graphics.Bitmap

interface QRCodeGenerator {

    fun generate(
        content: String,
        options: QRCodeOptions = QRCodeOptions()
    ): Bitmap

}