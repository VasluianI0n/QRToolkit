package com.divergentapp.qrtoolkit.core.qr

import com.divergentapp.qrtoolkit.domain.model.QRFormat
import com.google.mlkit.vision.barcode.common.Barcode

object QRFormatMapper {

    fun fromBarcode(
        format: Int
    ): QRFormat {

        return when (format) {

            Barcode.FORMAT_QR_CODE -> QRFormat.QR_CODE

            Barcode.FORMAT_AZTEC -> QRFormat.AZTEC

            Barcode.FORMAT_DATA_MATRIX -> QRFormat.DATA_MATRIX

            Barcode.FORMAT_PDF417 -> QRFormat.PDF417

            Barcode.FORMAT_CODE_128 -> QRFormat.CODE_128

            Barcode.FORMAT_CODE_39 -> QRFormat.CODE_39

            Barcode.FORMAT_CODE_93 -> QRFormat.CODE_93

            Barcode.FORMAT_CODABAR -> QRFormat.CODABAR

            Barcode.FORMAT_EAN_8 -> QRFormat.EAN_8

            Barcode.FORMAT_EAN_13 -> QRFormat.EAN_13

            Barcode.FORMAT_ITF -> QRFormat.ITF

            Barcode.FORMAT_UPC_A -> QRFormat.UPC_A

            Barcode.FORMAT_UPC_E -> QRFormat.UPC_E

            else -> QRFormat.UNKNOWN

        }

    }

}