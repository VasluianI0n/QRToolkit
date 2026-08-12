package com.divergentapp.qrtoolkit.domain.usecase

import android.graphics.Bitmap
import com.divergentapp.qrtoolkit.core.qr.QRCodeGenerator
import com.divergentapp.qrtoolkit.core.qr.QRCodeOptions
import com.divergentapp.qrtoolkit.core.qr.QRContentEncoder
import com.divergentapp.qrtoolkit.domain.model.QRContent

class GenerateQRUseCase(
    private val encoder: QRContentEncoder,
    private val generator: QRCodeGenerator
) {

    operator fun invoke(
        content: QRContent,
        options: QRCodeOptions = QRCodeOptions()
    ): Bitmap {

        val encoded = encoder.encode(content)

        return generator.generate(
            content = encoded,
            options = options
        )
    }
}