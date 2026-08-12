package com.divergentapp.qrtoolkit.core.qr

import com.divergentapp.qrtoolkit.domain.model.QRContent
import com.google.mlkit.vision.barcode.common.Barcode

class QRParserImpl : QRParser {

    override fun parse(barcode: Barcode): ParsedQR {

        val rawValue = barcode.rawValue.orEmpty()

        val content = when {

            barcode.url != null ->
                barcode.url!!.toContent()

            barcode.email != null ->
                barcode.email!!.toContent()

            barcode.phone != null ->
                barcode.phone!!.toContent()

            barcode.sms != null ->
                barcode.sms!!.toContent()

            barcode.wifi != null ->
                barcode.wifi!!.toContent()

            barcode.geoPoint != null ->
                barcode.geoPoint!!.toContent()

            barcode.contactInfo != null ->
                barcode.contactInfo!!.toContent()

            barcode.calendarEvent != null ->
                barcode.calendarEvent!!.toContent()

            else ->
                QRContent.Text(rawValue)

        }

        return ParsedQR(
            content = content,
            format = QRFormatMapper.fromBarcode(barcode.format),
            rawValue = rawValue
        )
    }

}