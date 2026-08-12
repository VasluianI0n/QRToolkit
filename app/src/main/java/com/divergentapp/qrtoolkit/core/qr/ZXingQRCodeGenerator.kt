package com.divergentapp.qrtoolkit.core.qr

import android.graphics.Bitmap
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import androidx.core.graphics.createBitmap
import androidx.core.graphics.set
import com.google.zxing.EncodeHintType

class ZXingQRCodeGenerator : QRCodeGenerator {


    override fun generate(
        content: String,
        options: QRCodeOptions
    ): Bitmap {

        val hints = mapOf(

            EncodeHintType.MARGIN to options.margin,

            EncodeHintType.ERROR_CORRECTION to options.errorCorrection

        )

        val matrix = QRCodeWriter().encode(
            content,
            BarcodeFormat.QR_CODE,
            options.size,
            options.size,
            hints
        )

        val bitmap = createBitmap(options.size, options.size)

        for (x in 0 until options.size) {
            for (y in 0 until options.size) {

                bitmap[x, y] = if (matrix[x, y]) {
                    options.foregroundColor
                } else {
                    options.backgroundColor
                }
            }
        }

        return bitmap
    }
}