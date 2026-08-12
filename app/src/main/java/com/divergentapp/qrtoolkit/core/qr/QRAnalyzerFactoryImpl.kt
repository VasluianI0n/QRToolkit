package com.divergentapp.qrtoolkit.core.qr

import com.divergentapp.qrtoolkit.core.camera.analyzer.QRAnalyzer
import com.divergentapp.qrtoolkit.core.scanner.ScannerSession
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode

class QRAnalyzerFactoryImpl(
    private val parser: QRParser,
    private val scannerSession: ScannerSession
) : QRAnalyzerFactory {

    override fun create(
        onResult: (ParsedQR) -> Unit
    ): QRAnalyzer {

        val scanner = BarcodeScanning.getClient(
            BarcodeScannerOptions.Builder()
                .setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS)
                .build()
        )

        return QRAnalyzer(
            scanner,
            parser,
            scannerSession,
            onResult
        )
    }
}