package com.divergentapp.qrtoolkit.core.camera.analyzer

import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.divergentapp.qrtoolkit.core.qr.ParsedQR
import com.divergentapp.qrtoolkit.core.qr.QRParser
import com.divergentapp.qrtoolkit.core.scanner.ScannerSession
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.common.InputImage
import java.util.concurrent.atomic.AtomicBoolean

class QRAnalyzer(

    private val scanner: BarcodeScanner,

    private val parser: QRParser,

    private val session: ScannerSession,

    private val onResult: (ParsedQR) -> Unit

) : ImageAnalysis.Analyzer {

    private val processing = AtomicBoolean(false)

    @ExperimentalGetImage
    override fun analyze(imageProxy: ImageProxy) {

        if (!session.isScanning) {

            imageProxy.close()

            return

        }

        val mediaImage = imageProxy.image

        if (mediaImage == null) {
            imageProxy.close()
            return
        }

        if (!processing.compareAndSet(false, true)) {
            imageProxy.close()
            return
        }

        val image = InputImage.fromMediaImage(
            mediaImage,
            imageProxy.imageInfo.rotationDegrees
        )

        scanner.process(image)
            .addOnSuccessListener { barcodes ->

                barcodes.firstOrNull()?.let {

                    session.pause()

                    onResult(
                        parser.parse(it)
                    )

                }

            }
            .addOnCompleteListener {

                processing.set(false)

                imageProxy.close()

            }

    }

}