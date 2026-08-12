package com.divergentapp.qrtoolkit.core.qr

import android.content.Context
import android.net.Uri
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class GalleryQrScannerImpl(
    private val context: Context,
    private val qrParser: QRParser
) : GalleryQrScanner {

    override suspend fun scan(
        uri: Uri
    ): ParsedQR? = suspendCancellableCoroutine { continuation ->

        val barcodeScanner by lazy(LazyThreadSafetyMode.NONE) {
            BarcodeScanning.getClient()
        }

        val image = InputImage.fromFilePath(
            context,
            uri
        )

        barcodeScanner
            .process(image)
            .addOnSuccessListener { barcodes ->

                val barcode = barcodes.firstOrNull()

                continuation.resume(
                    barcode?.let(qrParser::parse)
                )

                barcodeScanner.close()
            }
            .addOnFailureListener {

                continuation.resume(null)

                barcodeScanner.close()
            }
    }
}