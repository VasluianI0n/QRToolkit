package com.divergentapp.qrtoolkit.core.qr

import com.divergentapp.qrtoolkit.core.camera.analyzer.QRAnalyzer

interface QRAnalyzerFactory {
    fun create(
        onResult: (ParsedQR) -> Unit
    ): QRAnalyzer
}