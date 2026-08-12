package com.divergentapp.qrtoolkit.core.qr

import com.google.mlkit.vision.barcode.common.Barcode

interface QRParser {

    fun parse(barcode: Barcode): ParsedQR

}